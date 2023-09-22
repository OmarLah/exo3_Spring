package com.example.exo_3.mappers;

import com.example.exo_3.entities.Contact;
import com.example.exo_3.models.ContactDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;

@Mapper
public interface ContactMapper {


    Contact contactDtoToContact(ContactDTO dto);


    @Mapping(source = "birthDate", target = "birthDate",  qualifiedByName = "convertDateToString")
    @Mapping(source = "birthDate", target = "age",  qualifiedByName = "convertDateToAge")
    ContactDTO contactToContactDto(Contact contact);





    @Named("convertDateToAge")
    public static Integer convertDateToAge(LocalDate date) {
        LocalDate now = LocalDate.now();
        Integer age = now.getYear() - date.getYear();

        if (now.minusYears(age).isBefore(date)) {
            age--;
        }

        return age;
    }


    @Named("convertDateToString")
    public static String convertDateToString(LocalDate date) {

        String string = date.toString();
        return string;

    }
}
