package com.project.pickItUp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PickItUpApplication {

	public static void main(String[] args) {
		SpringApplication.run(PickItUpApplication.class, args);
	}

}

//	Another is to just have a CityId in the Users table. This, in turn,
//	would reference States, which in turn would reference Country.
//	In usual practice, cities are in a single state and states are in a single country.
//	You do not want to make it easy to violate this constraint, and this structure prevents that from happening.