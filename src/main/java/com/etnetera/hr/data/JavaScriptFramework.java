package com.etnetera.hr.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Simple data entity describing basic properties of every JavaScript framework.
 * 
 * @author Etnetera
 *
 */
@Entity
@NoArgsConstructor
public @Data class JavaScriptFramework {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 30)
	private String name;

	@Column
	private LocalDate deprecationDate;

	@Column(nullable = false, length = 10)
	private String version;

	@Column()
	private Integer hypeLevel;

	public JavaScriptFramework(String name, LocalDate deprecationDate, String version, Integer hypeLevel) {
		this.name = name;
		this.deprecationDate = deprecationDate;
		this.version = version;
		this.hypeLevel = hypeLevel;
	}
}
