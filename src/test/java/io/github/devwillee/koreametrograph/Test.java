package io.github.devwillee.koreametrograph;

import io.github.devwillee.koreametrograph.api.MetroGraph;
import io.github.devwillee.koreametrograph.api.MetroGraphFactory;
import io.github.devwillee.koreametrograph.cities.seoul.SeoulMetroGraphFactory;

public class Test {

	public static void main(String[] args) {
		MetroGraph seoulMetro = MetroGraphFactory.create(SeoulMetroGraphFactory.class);

		/*seoulMetro.find("신도림").forEach(System.out::println);
		System.out.println();
		seoulMetro.find("신도림", "1").forEach(System.out::println);*//*
		//seoulMetro.find("소사").forEach(System.out::println);*/
	}
}