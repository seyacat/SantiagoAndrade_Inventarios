package com.example.SantiagoAndrade_Inventarios.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.example.SantiagoAndrade_Inventarios.SantiagoAndradeInventariosApplication;

import com.fasterxml.jackson.core.JsonProcessingException;

@Entity
@Table(name="reports")
class Orders{
	@Id
	public String id;
	public Integer count;
	public String store;
	public String date;
}

@Entity
@Table(name="reports")
class Sells{
	@Id
	public String id;
	public String store;
	public Integer count;
	public String cod;
	public String product;
}

@Entity
@Table(name="reports")
class OrderDetail{
	@Id
	public String id;
	public String order_id;
	public String store;
	public String store_cod;
	public String product;
	public String product_cod;
	public Integer stock_at_time;
	public Integer qty;
	public String date;
}

@Controller 
@RequestMapping(path="/report") 
public class ReportController {
  @Autowired         
  
  @PersistenceContext
  EntityManager em;
  
  private static final Logger log = LoggerFactory.getLogger(SantiagoAndradeInventariosApplication.class);
  
  @SuppressWarnings("unchecked")
  @GetMapping(path="/orders")
  public @ResponseBody List<Orders> getOrderCountByStoreDate() throws JsonProcessingException {
	  Query query = em.createNativeQuery(  "select md5(CONCAT(s.id,\"_\",date(o.date))) as id,  count(*) as count, s.name as store , date(o.date) as date from product_order po\r\n"
				+ "left join order_ o on po.order_id = o.id \r\n"
				+ "left join store s on po.store_id = s.id \r\n"
				+ "group by s.id, date(o.date)\r\n"
				+ "order by s.id, o.date",Orders.class); 
	  return  query.getResultList() ;
  }
  
  @SuppressWarnings("unchecked")
  @GetMapping(path="/sells")
  public @ResponseBody List<Sells> getCountStoreProduct() throws JsonProcessingException {
	  Query query = em.createNativeQuery(  "select md5(CONCAT(s.id,\"_\",p.id)) as id,  s.name as store, count(*) as count, p.cod, p.name as product  from product_order po\r\n"
				+ "left join product p on po.product_id = p.id \r\n"
				+ "left join order_ o on po.order_id = o.id \r\n"
				+ "left join store s on po.store_id = s.id \r\n"
				+ "group by p.id,s.id\r\n"
				+ "order by s.id",Sells.class);  
	   return  query.getResultList();
    
  }
  
  @SuppressWarnings("unchecked")
  @GetMapping(path="/csv")
  public @ResponseBody void getOrdersDetail(HttpServletResponse servletResponse, @RequestParam("start") String start,@RequestParam("end") String end) throws IOException {
	  
	  
	  LocalDate startDate;
	  LocalDate endDate;
	  try {
		  startDate = LocalDate.parse(start);
		  endDate = LocalDate.parse(end);
		  log.info( startDate.format(DateTimeFormatter.ISO_DATE) );
		  log.info( endDate.format(DateTimeFormatter.ISO_DATE) );
	  }catch(Exception e) {
		  throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Wrong Dates");
	  }
	 
	  
	  Query query = em.createNativeQuery(  "select md5(po.id) as id,o.id as order_id, s.name as store, s.cod as store_cod,  p.name as product, p.cod product_cod, p.stock as stock_at_time, po.qty , o.date  from product_order po\r\n"
	  		+ "left join product p on po.product_id = p.id \r\n"
	  		+ "left join order_ o on po.order_id = o.id \r\n"
	  		+ "left join store s on po.store_id = s.id \r\n"
	  		+ "where DATE(o.date) >= :startdate && DATE(o.date) <= :enddate \r\n"
	  		+ "order by o.date",OrderDetail.class); 
	   query.setParameter("startdate",startDate.format(DateTimeFormatter.ISO_DATE) );
	   query.setParameter("enddate",endDate.format(DateTimeFormatter.ISO_DATE) );
	   List<OrderDetail> orderDetails =  query.getResultList();
	   
	   servletResponse.setContentType("text/csv");
       servletResponse.addHeader("Content-Disposition","attachment; filename=\"order_detail.csv\"");
       
       servletResponse.getWriter().write("id,order_id,store,store_code,product,product_cod,stock_as_time,qty,date\n");
       
       for(OrderDetail row : orderDetails ) {
    	   servletResponse.getWriter().write(row.id+","+row.order_id+","+row.store+","+row.store_cod+","+
    			   	row.product+","+row.product_cod+","+row.stock_at_time+","+row.qty+","+row.date+"\n");
       }
         
    
  }
  
  
 
  
}

