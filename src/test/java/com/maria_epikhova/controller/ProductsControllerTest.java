package com.maria_epikhova.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.EasyTcpApplication;
import com.framework.annotations.EasyTcpMain;
import com.framework.models.Response;
import com.maria_epikhova.App;
import com.maria_epikhova.exceptions.InvalidRequestException;
import com.maria_epikhova.models.Product;
import com.maria_epikhova.models.User;
import com.maria_epikhova.repos.ProductRepo;
import com.maria_epikhova.repos.UsersRepo;

import org.junit.jupiter.api.Test;

@EasyTcpMain

public class ProductsControllerTest {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ProductsControllerTest() throws UnknownHostException, IOException {
        EasyTcpApplication.run(App.class, 4321);
        socket = new Socket("localhost", 4321);

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

    }

    @Test
    void testAddAction() throws ClassNotFoundException, IOException, InvalidRequestException {
        String req = "{\"action\": \"/users/SIGN_IN\",\"data\": {\"email\": \"some@gmail.com\",\"password\": \"12345\"}}";

        out.writeObject(req);
        String json = (String) in.readObject();
        Response response = new ObjectMapper().readValue(json, Response.class);
        String token = (String) response.getData();

        out.writeObject(
                "{\"token\": \"" + token
                        + "\",\"action\": \"/products/ADD\",\"data\": {\"name\": \"Test\",\"price\": 150, \"size\": \"S\", \"type\": \"DRESS\", \"gender\": \"MEN\", \"color\": \"red\", \"description\": \"DASASDASD\", \"imageUrl\": \"http:image.com\", \"requirement\": {\"frequency\": 5, \"count\": 12, \"specialRequirements\": \"ASDASDASD\"}}}");
        json = (String) in.readObject();
        System.out.println(json);
        Response res = new ObjectMapper().readValue(json, Response.class);
        assertEquals(true, res.getData());

        ProductRepo repo = new ProductRepo();
        List<Product> products = repo.getAllProducts();
        for (Product product : products) {
            if (product.getName().equals("Test")) {
                System.out.println("DELETED");
                repo.deleteProduct(product.getProductId());
            }
        }
        socket.close();

        // deleteUserByEmail();
    }

    void deleteUserByEmail() throws InvalidRequestException {
        UsersRepo repo = new UsersRepo();

        User user = repo.getByEmail("some@gmail.com");

        repo.deleteUser(user.getId());
    }

    void createUser() throws IOException {
        String req = "{\"action\": \"/users/ADD\",\"data\": {\"email\": \"some@gmail.com\",\"password\": \"12345\",\"secretQuestion\": \"2+2\",\"secretAnswer\": \"4\",\"userRole\": \"SUPPLIER\"}}";
        out.writeObject(req);
    }
}
