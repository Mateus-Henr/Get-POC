package com.ufv.project.db;

import com.ufv.project.model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Singleton
{
    private final ObservableList<User> userList;
    private final ObservableList<POC> pocList;

    private Singleton()
    {
        userList = FXCollections.observableArrayList();
        pocList = FXCollections.observableArrayList();

        pocList.add(new POC.POCBuilder()
                .title("My")
                .defenseDate(LocalDate.now())
                .advisor(new Professor("matt", "Matt", "kdka", "da", new ArrayList<>()))
                .coAdvisors(new ArrayList<>())
                .registrant(new Professor("matt", "Matt", "kdka", "da", new ArrayList<>()))
                .pdf(new PDF(1, new File(""), LocalDate.now()))
                .field(new Field(1, "sa"))
                .summary("dadasdasdas")
                .keywords(new ArrayList<>())
                .build());

        userList.add(new Professor("matt", "Matt", "kdka", "da", new ArrayList<>()));
        userList.add(new Professor("matt", "Matt", "kdka", "da", new ArrayList<>()));
    }

    private static class RegistryHolder
    {
        static Singleton INSTANCE = new Singleton();

    }

    public static Singleton getInstance()
    {
        return RegistryHolder.INSTANCE;
    }

    public ObservableList<User> getUserList()
    {
        return FXCollections.unmodifiableObservableList(userList);
    }

    public ObservableList<Professor> getProfessorList()
    {
        return FXCollections.unmodifiableObservableList(FXCollections.observableList(userList.stream()
                .filter(user -> user.getUserType() == UserTypesEnum.PROFESSOR)
                .map(user -> (Professor) user).toList()));
    }

    public ObservableList<Professor> getStudentList()
    {
        return FXCollections.unmodifiableObservableList(FXCollections.observableList(userList.stream()
                .filter(user -> user.getUserType() == UserTypesEnum.STUDENT)
                .map(user -> (Professor) user).toList()));
    }

    public ObservableList<POC> getPocList()
    {
        return FXCollections.unmodifiableObservableList(pocList);
    }

    public boolean addUser(User user)
    {
        boolean userAlreadyExists = isUserInDatabase(user.getUsername());

        if (!userAlreadyExists)
        {
            return userList.add(user);
        }

        return false;
    }

    public User getUser(String username)
    {
        for (User user : userList)
        {
            if (user.getUsername().equals(username))
            {
                return user;
            }
        }

        return null;
    }

    public boolean removeUser(String username)
    {
        return userList.removeIf(user -> user.getUsername().equals(username));
    }

    public boolean addPOC(POC poc)
    {
        return pocList.add(poc);
    }

    public POC getPOC(int pocId)
    {
        for (POC poc : pocList)
        {
            if (poc.getId() == pocId)
            {
                return poc;
            }
        }

        return null;
    }

    public boolean removePOC(int pocId)
    {
        return pocList.removeIf(poc -> poc.getId() == pocId);
    }

    public boolean isUserInDatabase(String username)
    {
        return getUser(username) != null;
    }

}