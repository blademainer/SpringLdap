package com.xiongyingqi.repo;

import com.xiongyingqi.BaseTest;
import com.xiongyingqi.model.Person;
import com.xiongyingqi.util.EntityHelper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by xiongyingqi on 14-3-12.
 */

public class PersonRepoTest extends BaseTest{
    @Autowired
    private PersonRepo personRepo;

    @Test
    public void testGetAllPersonNames(){
        List<String> allPersonNames = personRepo.getAllPersonNames();
        System.out.println("allPersonNames ========= " + allPersonNames);
        Assert.assertNotNull(allPersonNames);
    }

    @Test
    public void testGetAllPersons(){
        List<Person> allPersons = personRepo.getAllPersons();
        for(Person person : allPersons){
            EntityHelper.print(person);
        }
        Assert.assertNotNull(allPersons);
    }

    
}
