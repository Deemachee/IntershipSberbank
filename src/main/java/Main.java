import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        CityUtils cityUtils = new CityUtils();

        // получаем список городов из файла
        List<City> cities = cityUtils.readCities();

        //выводим на экран список городов отсортированных по округу и имени по убыванию с учетом регистра
        cityUtils.printCities(cityUtils.getCitiesSortedByDistrictAndName(cities));

        System.out.println("\n".repeat(2));

        //выводим на экран список городов отсортированных по имени по убыванию без учета регистра
        cityUtils.printCities(cityUtils.getCitiesSortedByName(cities));

        System.out.println("\n".repeat(2));

        /**
         * выводим на экран порядковый номер и значение максимальной
         * численности населения в считанном списке,
         * преобразованном в массив
         */

        System.out.println("[" + cityUtils.getCityWithMaxPopulation(cities).keySet() + "] = " +
                cityUtils.getCityWithMaxPopulation(cities).values());

        System.out.println("\n".repeat(2));

        /**
         * получаем мапу, где ключ - округ, значение - количество городов
         * и выводим на экран
         */

        Map<String, Integer> mapOfCities = cityUtils.calculateCitiesOfDistrict(cities);
        for (Map.Entry<String, Integer> pair : mapOfCities.entrySet()) {
            String district = pair.getKey();
            Integer count = pair.getValue();
            System.out.println(district + " - " + count);
        }
    }
}
