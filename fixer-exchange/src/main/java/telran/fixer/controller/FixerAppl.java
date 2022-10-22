package telran.fixer.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import telran.fixer.dto.ResponseFixerDto;

public class FixerAppl {
    static String from;
    static String to;
    static double amount;
    static RestTemplate restTemplate = new RestTemplate();
    private static String baseUrl = "https://api.apilayer.com/fixer/convert";
    private static final String apiKey = "uL1pzgmw0sQCeGbJ6xjALG6S3czcjdu5";

    public static void main(String[] args) throws IOException {
	Menu();
	double res = convertCurrency();
	System.out.println(res+ " res");
    }

    private static double convertCurrency() {
	HttpHeaders headers = new HttpHeaders();
	headers.add("apikey", apiKey);
	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl).queryParam("from", from)
		.queryParam("to", to).queryParam("amount", amount);
	RequestEntity<String> request = new RequestEntity<>(headers, HttpMethod.GET, builder.build().toUri());
	ResponseEntity<ResponseFixerDto> responseEntity = restTemplate.exchange(request, ResponseFixerDto.class);
	double result = responseEntity.getBody().getResult();

	return result;

    }

    private static void Menu() throws IOException {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	// number section
	System.out.println("Enter amount: ");
	String number = br.readLine();
	while (!isNumber(number)) {
	    System.out.println("Enter numbers only!: ");
	    number = br.readLine();
	}
	amount = Double.valueOf(number);

	// from currency
	System.out.println("Enter from which currency: ");
	String from = br.readLine().toUpperCase();
	while (from.length() != 3) {
	    System.out.println("Check currency code and try again");
	    from = br.readLine().toUpperCase();
	}

	// to currency
	System.out.println("to which currency: ");
	String to = br.readLine().toUpperCase();
	while (to.length() != 3) {
	    System.out.println("Check currency code and try again");
	    to = br.readLine().toUpperCase();
	}
	br.close();

    }

    public static boolean isNumber(String number) {
	if (number == null) {
	    return false;
	}
	try {
	    double d = Double.parseDouble(number);
	} catch (NumberFormatException nfe) {
	    return false;
	}
	return true;
    }

}
