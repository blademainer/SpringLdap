package com.xiongyingqi.repo;

import com.xiongyingqi.model.Person;
import com.xiongyingqi.util.PropertiesNameCollect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Component;

import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * Created by xiongyingqi on 14-3-12.
 */
@Component
public class PersonRepoImpl implements PersonRepo {
    public static final String BASE_DN = "DC=sz,DC=kr,DC=com";
    @Autowired
    private LdapTemplate ldapTemplate;

    @Override
    public List<String> getAllPersonNames() {
        System.out.println(ldapTemplate.toString());
        LdapQuery query = query()
                .base("CN=users");
        List<String> list = ldapTemplate.list(query.base());
        System.out.println("list ==== " + list);

        return ldapTemplate.search(
                query().base("CN=users").where("objectclass").is("person"), new AttributesMapperImpl());
//        return ldapTemplate.search("CN=USERS", "(objectclass=person)", new AttributesMapperImpl());
    }

    @Override
    public List<Person> getAllPersons() {
        return ldapTemplate.search(
                query().base("CN=users").where("objectclass").is("person"), new PersonAttributesMapper());
    }


    public void create(Person p) {
        Name dn = buildDn(p);
        ldapTemplate.bind(dn, null, buildAttributes(p));
    }

    public void delete(Person p) {
        Name dn = buildDn(p);
        ldapTemplate.unbind(dn);
    }

    public void update(Person p) {
        Name dn = buildDn(p);
        ldapTemplate.rebind(dn, null, buildAttributes(p));
    }

    public void updateDescription(Person p) {
        Name dn = buildDn(p);
        Attribute attr = new BasicAttribute("description", p.getDescription());
        ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);
        ldapTemplate.modifyAttributes(dn, new ModificationItem[]{item});
    }

    private Attributes buildAttributes(Person p) {
        Attributes attrs = new BasicAttributes();
        BasicAttribute ocattr = new BasicAttribute("objectclass");
        ocattr.add("top");
        ocattr.add("person");
        attrs.put(ocattr);
        attrs.put("cn", "Some Person");
        attrs.put("sn", "Person");
        return attrs;
    }

    protected Name buildDn(Person p) {
        return LdapNameBuilder.newInstance(BASE_DN)
                .add("c", p.getCountry())
                .add("ou", p.getCompany())
                .add("cn", p.getFullName())
                .add("description", p.getDescription())
                .add("userPrincipalName", p.getUserPrincipalName())
                .build();
    }

    protected Person buildPerson(Name dn, Attributes attrs) {
        Person person = new Person();
        person.setCountry(LdapUtils.getStringValue(dn, "c"));
        person.setCompany(LdapUtils.getStringValue(dn, "ou"));
        person.setFullName(LdapUtils.getStringValue(dn, "cn"));
        person.setUserPrincipalName(LdapUtils.getStringValue(dn, "userPrincipalName"));
        person.setDescription(LdapUtils.getStringValue(dn, "description"));
        // Populate rest of person object using attributes.

        return person;
    }

    private class PersonAttributesMapper implements AttributesMapper<Person> {

        @Override
        public Person mapFromAttributes(Attributes attributes) throws NamingException {
            Person person = new Person();
            person.setFullName(attributes.get("cn").get().toString());
            if (attributes.get("description") != null) {
                person.setDescription(attributes.get("description").get().toString());
            }
            if (attributes.get("userPrincipalName") != null) {
                person.setUserPrincipalName(attributes.get("userPrincipalName").get().toString());
            }
            return person;
        }
    }

    class AttributesMapperImpl implements AttributesMapper<String> {

        @Override
        public String mapFromAttributes(Attributes attrs)
                throws NamingException {
            NamingEnumeration<? extends Attribute> all = attrs.getAll();
            NamingEnumeration<String> ids = attrs.getIDs();
            Attribute cn = attrs.get("cn");
            System.out.println("cn ======= " + cn.get());

            while (all.hasMore()) {
                Attribute id = all.next();
                System.out.println(id.getID() + " ===== " + id.get());
//                PropertiesNameCollect.someName(id.getID());
            }

            return attrs.get("cn").get().toString();
        }
    }
}
