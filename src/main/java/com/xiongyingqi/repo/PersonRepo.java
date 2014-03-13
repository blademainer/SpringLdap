package com.xiongyingqi.repo;

import com.xiongyingqi.model.Person;

import java.util.List;

/**
 * Created by xiongyingqi on 14-3-12.
 */
public interface PersonRepo {
    public List<String> getAllPersonNames();
    public List<Person> getAllPersons();
}
