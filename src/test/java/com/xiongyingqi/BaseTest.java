package com.xiongyingqi;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.unitils.UnitilsJUnit4;

//@SpringApplicationContext({ "classpath:/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-ldap.xml"})
public class BaseTest extends UnitilsJUnit4 {
}
