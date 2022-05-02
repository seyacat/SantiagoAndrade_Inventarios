package com.example.SantiagoAndrade_Inventarios.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.example.SantiagoAndrade_Inventarios.model.Client;
import com.example.SantiagoAndrade_Inventarios.model.Order;
import com.example.SantiagoAndrade_Inventarios.model.Product;
import com.example.SantiagoAndrade_Inventarios.model.ProductOrder;
import com.example.SantiagoAndrade_Inventarios.model.Store;
import com.example.SantiagoAndrade_Inventarios.repository.ClientRepository;
import com.example.SantiagoAndrade_Inventarios.repository.OrderRepository;
import com.example.SantiagoAndrade_Inventarios.repository.ProductOrderRepository;
import com.example.SantiagoAndrade_Inventarios.repository.ProductRepository;
import com.example.SantiagoAndrade_Inventarios.repository.StoreRepository;
import com.example.SantiagoAndrade_Inventarios.service.StockService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.media.Schema;

@Controller
@RequestMapping(path = "/order")
public class OrderController {
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductOrderRepository productOrderRepository;
	@Autowired
	private StockService stockService;

	@PostMapping(path = "/create") // Map ONLY POST Requests
	public @ResponseBody Order createOrder(@RequestBody NewOrder obpayload)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode payload = mapper.valueToTree(obpayload);

		Iterator<JsonNode> storesit = payload.get("stores").elements();

		Client client;
		try {
			client = clientRepository.findById(payload.get("client_id").asInt()).get();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client not exists", e);
		}

		Order order = new Order(client);
		List<ProductOrder> productOrders = new ArrayList<>();

		while (storesit.hasNext()) {
			JsonNode storeJson = storesit.next();
			Store store;
			try {
				store = storeRepository.findById(storeJson.get("store_id").asInt()).get();
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store not exists", e);
			}

			Set<Product> storeProducts = store.getProducts();
			Iterator<JsonNode> prodsit = storeJson.get("products").elements();
			while (prodsit.hasNext()) {
				JsonNode prodJson = prodsit.next();
				Product product;
				try {
					product = productRepository.findById(prodJson.get("id").asInt()).get();
				} catch (Exception e) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not exists", e);
				}
				if (!storeProducts.contains(product)) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store don't manage this product");
				}

				Integer qty = prodJson.get("qty").asInt();
				if (qty <= 0) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quiantity Error");
				}
				if (product.getStock() - qty < -10) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unidades no disponibles");
				}

				ProductOrder productOrder = new ProductOrder(qty, product, store, order);
				productOrders.add(productOrder);
			}
		}

		// SAVE IF NOT ERRORS
		orderRepository.save(order);
		for (ProductOrder po : productOrders) {
			Product product = po.getProduct();
			if (product.getStock() - po.getQty() < -10) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unidades no disponibles");
			} else if (product.getStock() - po.getQty() < -5) {
				product.addStock(-po.getQty());
				productRepository.save(product);
				// ADD STOCK SYNCRONOUS CALL
				stockService.RequestStock(product.getId(), true);
			} else if (product.getStock() - po.getQty() < 0) {
				product.addStock(-po.getQty());
				productRepository.save(product);
				// ADD STOCK ASYNCRONOUS CALL
				stockService.RequestStockAsync(product.getId(), false);
			}
			productOrderRepository.save(po);
		}
		return order;
	}
}

@Schema(hidden = true)
class NewOrder {
	public Integer client_id;
	public NewStores[] stores;
}

@Schema(hidden = true)
class NewStores {
	public Integer store_id;
	public NewProducts[] products;
}

@Schema(hidden = true)
class NewProducts {
	public Integer id;
	public Integer qty;
}
//{\"client_id\":1,\"stores\":[{\"store_id\":1,\"products\":[{\"id\":1,\"qty\":8}]}]}