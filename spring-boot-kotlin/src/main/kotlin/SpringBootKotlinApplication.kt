package com.tistory.offbyone.springboot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class SpringBootKotlinApplication

fun main(args: Array<String>) {
	runApplication<SpringBootKotlinApplication>(*args)
}
