package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.*;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );

//        .toLocalDate();
//        .toLocalTime();
      List<UserMealWithExceed> mealSorted = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 5000);
        mealSorted.forEach(System.out::println);

        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }


    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field

         Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        for (UserMeal userMeal : mealList) {
            caloriesSumByDate.merge(userMeal.getDate(), userMeal.getCalories(), Integer::sum);
        }

         List<UserMealWithExceed> mealSorted = new ArrayList<>();
        for (UserMeal meal : mealList) {
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealSorted.add(VerifyExcess(meal,caloriesSumByDate.get(meal.getDate()) > caloriesPerDay));
            }
        }
        return mealSorted;



    }
    private static UserMealWithExceed VerifyExcess(UserMeal meal, boolean excess) {
        return new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
