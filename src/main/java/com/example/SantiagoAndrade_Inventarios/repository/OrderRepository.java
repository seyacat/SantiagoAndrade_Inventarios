package com.example.SantiagoAndrade_Inventarios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.SantiagoAndrade_Inventarios.model.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {

	@Query(nativeQuery = true, value ="select  count(*) as count, s.name as store , date(o.date) as date from product_order po\r\n"
	+ "left join order_ o on po.order_id = o.id \r\n"
	+ "left join store s on po.store_id = s.id \r\n"
	+ "group by s.id, date(o.date)\r\n"
	+ "order by s.id, o.date")
	List<Object[]> getOrderCountByStoreDate();
	
	@Query(nativeQuery = true, value ="select  s.name as store, count(*) as count, p.cod, p.name as product  from product_order po\r\n"
			+ "left join product p on po.product_id = p.id \r\n"
			+ "left join order_ o on po.order_id = o.id \r\n"
			+ "left join store s on po.store_id = s.id \r\n"
			+ "group by p.id,s.id\r\n"
			+ "order by s.id")
	List<Object[]> getCountStoreProduct();
	
}