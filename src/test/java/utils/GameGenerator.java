package utils;

import com.github.javafaker.Faker;
import models.games.DlcsItem;
import models.games.GamesRoot;
import models.games.Requirements;
import models.games.SimilarDlc;

import java.time.LocalDateTime;
import java.util.*;


public class GameGenerator {

    private static final Faker faker = new Faker();


    public static GamesRoot generateGameFullData () {

        Random random = new Random();
        int randomNumber = Math.abs(random.nextInt());
        Requirements requirements = Requirements.builder()
                .videoCard("sdfsdf")
                .osName("asdasd")
                .hardDrive(faker.random().nextInt(10000))
                .ramGb(faker.random().nextInt(10))
                .build();

        SimilarDlc similarDlc = SimilarDlc.builder()
                .dlcNameFromAnotherGame(faker.funnyName().name())
                .isFree(true)
                .build();

        DlcsItem dlcsItem = DlcsItem.builder()
                .dlcName("Gdsfsdf")
                .isDlcFree(false)
                .similarDlc(similarDlc)
                .price(1223)
                .description(faker.name().name())
                .build();

        return  GamesRoot.builder()

                .price(faker.random().nextInt(1000))
                .isFree(false)
                .requiredAge(true)
                .publishDate(LocalDateTime.now().toString())
                .rating(15)
                .genre("male")
                .company("GameDev")
                .requirements(requirements)
                .title("Fddd")
                .tags(List.of("assdsad","sdsdddd"))
                .dlcs(List.of(dlcsItem))
                .description("Dfffff")
                .build();
    }

    }
