package utils;

import com.github.javafaker.Faker;
import models.gamesNew.DlcsItem;
import models.gamesNew.GamesRoot;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;

public class GameGenerator {

    private static final Faker faker = new Faker();

    DlcsItem dlc = DlcsItem.builder()



    public static GamesRoot generateGameFullData (){

        Date now = new Date();
        Date then = new Date(now.getTime() + 1000);

        Random random = new Random();
        int randomNamber = Math.abs(random.nextInt());

        List <DlcsItem> dlc = new ArrayList<>();

       return  GamesRoot.builder()
                .gameId(randomNamber)
                .price(randomNamber)
                .dlcs()
                .genre("male")
                .title(faker.internet().avatar())
                .company(faker.company().name())
                .requirements()
                .description()
                .isFree(true)
                .requiredAge(true)
                .publishDate(faker.date().between(then,now))
                .rating(15)
                .build();
    }

}
