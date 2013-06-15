package com.freematador.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import com.freematador.domain.Picture;
import com.freematador.domain.Product;

public @Stateless class ProductEJBBean implements ProductEJB {
	public List<Product> getHighlighedProducts() {
		List<Product> highlightedProducts = new ArrayList<Product>();
		Product product1 = new Product();
		product1.setBasePrice(100);
		product1.setId(1);
		product1.setLongDescription("BATERIA PARA CELULAR NOKIA / BL-5J / " +
				"Nokia 5800 X6 5230 5235 C3 5228 NUEVAS CON GARANTIA");
		product1.setShortDescription("Bateria Celular Nokia Bl-5j Bl5j Nokia 5800 X6 5230");
		Picture picture1 = new Picture();
		picture1.setDescription("Bateria");
		picture1.setFileName("bateria.jpg");
		product1.getPictures().add(picture1);
		
		Product product2 = new Product();
		product2.setBasePrice(200);
		product2.setId(2);
		product2.setShortDescription("Guante De Boxeo P/ Entrenamiento Bolsas,vendas, N1 En Venta");
		product2.setLongDescription("Guante De Boxeo P/ Entrenamiento Bolsas,vendas, N1 En Venta");
		Picture picture2 = new Picture();
		picture2.setDescription("Guantes");
		picture2.setFileName("guantes.jpg");
		product2.getPictures().add(picture2);
		
		Product product3 = new Product();
		product3.setBasePrice(600);
		product3.setId(3);
		product3.setShortDescription("Celular Smartphone Samsung Galaxy S3 Oferta Irresistible");
		product3.setLongDescription("Celular Smartphone Samsung Galaxy S3 Oferta Irresistible");
		Picture picture3 = new Picture();
		picture3.setDescription("Samsung Galaxy S3");
		picture3.setFileName("galaxys3.jpg");
		product3.getPictures().add(picture3);
		
		Product product4 = new Product();
		product4.setBasePrice(1800);
		product4.setId(4);
		product4.setShortDescription("The Last Of Us Ps3 - Llegada El 15 Junio- Nuevos Sellados-");
		product4.setLongDescription("The Last Of Us Ps3 - Llegada El 15 Junio- Nuevos Sellados-");
		Picture picture4 = new Picture();
		picture4.setDescription("The Last of Us PS3");
		picture4.setFileName("thelastofus.jpg");
		product4.getPictures().add(picture4);
		
		highlightedProducts.add(product1);
		highlightedProducts.add(product2);
		highlightedProducts.add(product3);
		highlightedProducts.add(product4);
		
		return highlightedProducts;
	}
}
