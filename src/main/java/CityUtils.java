import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CityUtils {

    //метод для создания списка городов
    public List<City> readCities() {
        List<City> cities = new ArrayList<>();
        try {
            //при указании пути "resources/city_ru.csv" idea не смогла найти файл, прописал полный путь
            Scanner scanner = new Scanner(new File("/Users/deemachee/" +
                    "IdeaProjects/Sberbank/ListOfCities/src/main/resources/city_ru.csv"));
            //пока есть строка, которую можно считывать
            while (scanner.hasNextLine()) {
                cities.add(createCity(scanner.nextLine()));
            }
            //закрываем поток
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return cities;
    }

    //builder для сущности City
    public City createCity(String line) {
        Scanner scanner = new Scanner(line);
        scanner.useDelimiter(";");
        //пропуск первого столбца, я в своем первом примере пропускал с помощью индекса
        scanner.skip("\\d*");
        String name = scanner.next();
        String region = scanner.next();
        String district = scanner.next();
        int population = scanner.nextInt();
        String foundation = null;
        if (scanner.hasNext()) {
            foundation = scanner.next();
        }
        scanner.close();
        return new City(name, region, district, population, foundation);
    }

    //метод для вывода консоль с помощью лямбды
    public void printCities(List<City> cities) {
        cities.forEach(System.out::println);
    }

    //метод для сортировки списка используя Компаратор
    public List<City> getCitiesSortedByName(List<City> cities) {
        cities.sort(Comparator.comparing(City::getName));
        return cities;
    }

    /**
     * метод для сортировки списка по федеральному округу и
     * наименованию города внутри каждого федерального округа в
     * алфавитном порядке по убыванию с учетом регистра
     */
    public List<City> getCitiesSortedByDistrictAndName(List<City> cities) {
        cities.sort((p1, p2) -> {
            if (p1.getName().compareTo(p2.getName()) == 0) {
                return p1.getName().compareTo(p2.getName());
            } else {
                return p1.getDistrict().compareTo(p2.getDistrict());
            }
        });
        return cities;
    }

    /**
     * метод для поиска номера города с максимальной численностью населения.
     * создаем массив из списка, ищем индекс города с максимальной численностью,
     * получившийся результат сохраняем в мапу, где ключ - номер, значение - максимальная численность
     */
    public Map<Integer, Integer> getCityWithMaxPopulation(List<City> cities) {
        City[] citiesArray = cities.toArray(new City[cities.size()]);
        int maxIndex = 0;
        for (int i = 1; i < citiesArray.length; i++) {
            if (citiesArray[i].getPopulation() > citiesArray[maxIndex].getPopulation()) {
                maxIndex = i;
            }
        }
        Map<Integer, Integer> result = new HashMap<>();
        result.put(maxIndex, citiesArray[maxIndex].getPopulation());
        return result;
    }


    /**
     * метод для поиска количества городов в округе.
     * создаем мапу, где ключ - название округа, значение - количество городов
     * проходимся по листу и добавляем элементы на основе ключа
     * используем TreeMap, чтобы сортировка производилась автоматически по ключу
     */
    public Map<String, Integer> calculateCitiesOfDistrict(List<City> cities) {
        Map<String, Integer> map = new TreeMap<>();

        for (City city : cities) {
            if (map.containsKey(city.getRegion())) {
                map.put(city.getRegion(), map.get(city.getRegion()) + 1);
            } else
                map.put(city.getRegion(), 1);
        }
        return map;

    }
}
