package net.savantly.example.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages="net.savantly.example.repository")
@ComponentScan("net.savantly.example.fixture")
public class SpringConfiguration {

	@Bean
	public DataSource dataSource(){
		EmbeddedDatabaseFactory edf = new EmbeddedDatabaseFactory();
		edf.setDatabaseType(EmbeddedDatabaseType.HSQL);
		return edf.getDatabase();
	}

	@Bean
	@Autowired
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource){
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setJpaVendorAdapter(createJpaVendorAdapter());
		emf.setJpaPropertyMap(getVendorProperties());
		emf.setPackagesToScan("net.savantly.example.entity");
		return emf;
	}
	
	@Bean(name="transactionManager")
	@Autowired
	public PlatformTransactionManager platformTransactionManager(EntityManagerFactory emf){
		PlatformTransactionManager ptm = new JpaTransactionManager(emf);
		return ptm;
	}
	
	protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
		EclipseLinkJpaVendorAdapter adapter = new EclipseLinkJpaVendorAdapter();
		adapter.setGenerateDdl(true);
		return adapter;
	}
	
	protected Map<String, Object> getVendorProperties() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("eclipselink.weaving", "false");
		//map.put("eclipselink.logging.level.sql", "FINE");
		return map;
	}
	
}
