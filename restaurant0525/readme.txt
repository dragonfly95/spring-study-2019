

mvc-config.xml 
-------------------------------------------------------
    <!-- @Transactional 애노테이션을 sacn하기 위한 설정 -->
    <tx:annotation-driven
            transaction-manager="transactionManager"/>

    <context:annotation-config/>

    <context:component-scan
            base-package="com.system.restaurant"/>
            
-------------------------------------------------------



application-context.xml
-------------------------------------------------------
    <!-- transaction과 연관되어 있음 -->
    <bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate" destroy-method="clearCache">
        <constructor-arg index="0" ref="sqlSessionFactory" />
    </bean>
    
    <!-- 트랜젝션 매니저 -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSourceSpied"></property>
    </bean>
    
-------------------------------------------------------
