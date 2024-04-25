package com.matrimony.repository;

import com.matrimony.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    public Page<Profile> findByReligionContainingAndUser_IdNot(String religion,int id, Pageable pageable);
    public Page<Profile> findByPlaceOfBirthContainingAndUser_IdNot(String city,int id, Pageable pageable);
    public Page<Profile> findByCasteContainingAndUser_IdNot(String caste, int id, Pageable pageable);
    public Optional<Profile> findByUserId(int id);

    public Page<Profile> findByUser_GenderAndCasteContainingAndReligionContainingAndPlaceOfBirthContainingAndHeightBetweenAndWeightBetweenAndUser_IdNot(int gender,String caste,String religion,String city,String minHeight, String maxHeight,String minWeight, String maxWeight, int id, Pageable pageable);

//    below is to search inside any object findByCasteContainingAndUser_GenderAndUser_IdNot findByUser_GenderAndUser_IdNot
public Page<Profile> findByUser_GenderAndCasteContainingAndReligionContainingAndPlaceOfBirthContainingAndUser_IdNot(int gender,String caste,String religion,String city,int id, Pageable pageable);
    public Page<Profile> findByUser_GenderAndCasteContainingAndUser_IdNot(int gender,String caste,int id, Pageable pageable);
    public Page<Profile> findByUser_GenderAndReligionContainingAndUser_IdNot(int gender,String religion,int id, Pageable pageable);
    public Page<Profile> findByUser_GenderAndPlaceOfBirthContainingAndUser_IdNot(int gender,String city,int id, Pageable pageable);

    public Page<Profile> findByUser_GenderAndUser_IdNot(int gender,int id, Pageable pageable);

    public Page<Profile> findByIdNot(int id, Pageable pageable);
    public Page<Profile> findByCasteContainingAndReligionContainingAndUser_IdNot(String name, String city, int id,Pageable pageable);

    public Page<Profile> findByHeightBetweenAndUser_IdNot(String minHeight, String maxHeight,int id, Pageable pageable);

    public Page<Profile> findByWeightBetweenAndUser_IdNot(String minWeight, String maxWeight,int id, Pageable pageable);

    public Page<Profile> findByAnnualIncomeBetweenAndUser_IdNot(String minAnnualIncome, String maxAnnualIncome,int id, Pageable pageable);

    public Page<Profile> findByUser_DateOfBirthBetweenAndUser_IdNot(Date minAge, Date maxAge, int id, Pageable pageable);

    public Page<Profile> findByHeightBetweenAndWeightBetweenAndUser_IdNot(String minHeight, String maxHeight,String minWeight, String maxWeight,int id, Pageable pageable);

//    public Page<Profile> findBySpokenLanguagesContainingAndUser_IdNot(String spokenLanguages, int id, Pageable pageable);
}
