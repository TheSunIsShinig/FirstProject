package org.example.service;

import org.assertj.core.api.AssertionsForClassTypes;
import org.example.models.Car;
import org.example.models.Human;
import org.example.repository.CarRepository;
import org.example.repository.HumanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock private CarRepository carRepository;
    @Mock private HumanRepository humanRepository;

    @InjectMocks private CarService underTest;

    @Test
    void getCars_ShouldGetAllCars() {
        //When
        underTest.getCars();

        //Then
        verify(carRepository).findAll();
    }

    @Test
    void getCarByID_ShouldGetGetCarByByID() {
        //Given
        UUID carID = UUID.randomUUID();
        Car car = new Car("MRS", "E-Class", "Blue", 800, 2020, 20000,1);
        when(carRepository.findById(carID)).thenReturn(Optional.of(car));

        //When
        underTest.getCarByID(carID);

        //Then
        verify(carRepository).findById(carID);
    }

    @Test
    void getCarByID_ShouldReturnException_WhenGetCarByDoesNotExist() {
        //Given
        UUID carID = UUID.randomUUID();

        //When & Then
        assertThatThrownBy(() -> underTest.getCarByID(carID))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Car not found with id" + carID);
    }

    @Test
    void carsBrand_ShouldReturnCarsBrand() {
        //Given
        String brand = "MRS";
        Car car1 = new Car(brand, "E-Class", "Blue", 800, 2020, 20000,1);
        Car car2 = new Car(brand, "E-Class", "Blue", 800, 2020, 20000,1);
        List<Car> cars = List.of(car1, car2);

        when(carRepository.findByBrand(brand)).thenReturn(cars);

        //When
        List <Car> result = underTest.carsBrand(brand);

        //Then
        verify(carRepository).findByBrand(brand);
        assertThat(result).isEqualTo(cars);

    }

    @Test
    void carsBrand_ShouldReturnEmptyList_WhenCarsNoFound() {
        //Given
        String brand = "MRS";
        List<Car> cars = List.of();

        when(carRepository.findByBrand(brand)).thenReturn(cars);

        //When
        List <Car> result = underTest.carsBrand(brand);

        //Then
        verify(carRepository).findByBrand(brand);
        assertThat(result).isEqualTo(cars);

    }

    @Test
    void carsPriceFromTo_ShouldReturnSortedCarByPrice() {
        //Given
        int min = 200;
        int max = 300;
        Car car = new Car("MRS", "E-Class", "Blue", 250, 2020, 20000,1);
        List<Car> cars = List.of(car);

        when(carRepository.findCarsByPriceRange(min, max)).thenReturn(cars);
        //When
        List<Car> result = underTest.carsPriceFromTo(min, max);

        //Then
        verify(carRepository).findCarsByPriceRange(min, max);
        assertThat(result).isEqualTo(cars);
    }

    @Test
    void carsPriceFromTo_ShouldReturnException_WhenMinPriceMoreThanMax() {
        //Given
        int min = 300;
        int max = 200;

        //When && Then
        assertThatThrownBy(()->underTest.carsPriceFromTo(min, max))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Minimum price cannot be greater than maximum price.");
    }

    @Test
    void carsPriceFromTo_ShouldReturnException_WhenMinPriceIsNegative() {
        //Given
        int min = -300;
        int max = 200;

        //When && Then
        assertThatThrownBy(()->underTest.carsPriceFromTo(min, max))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Minimum price cannot be negative.");
    }

    @Test
    void carsPriceFromTo_ShouldReturnException_WhenMaxPriceIsNegative() {
        //Given
        int min = 300;
        int max = -200;

        //When && Then
        assertThatThrownBy(()->underTest.carsPriceFromTo(min, max))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Maximum price cannot be negative.");
    }

    @Test
    void carsYearFromTo_ShouldReturnSortedCarByYear() {
        //Given
        int min = 2019;
        int max = 2021;
        Car car = new Car("MRS", "E-Class", "Blue", 250, 2020, 20000,1);
        List<Car> cars = List.of(car);

        when(carRepository.findCarsByYearRange(min, max)).thenReturn(cars);

        //When
        List<Car> result = underTest.carsYearFromTo(min, max);

        //Then
        verify(carRepository).findCarsByYearRange(min,max);
        assertThat(result).isEqualTo(cars);
    }

    @Test
    void carsYearFromTo_ShouldReturnException_WhenMinYearMoreThanMax() {
        //Given
        int min = 300;
        int max = 200;

        //When && Then
        assertThatThrownBy(()->underTest.carsYearFromTo(min, max))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Minimum year cannot be greater than maximum year.");
    }

    @Test
    void carsYearFromTo_ShouldReturnException_WhenMaxYearIsNegative() {
        //Given
        int min = 300;
        int max = -200;

        //When && Then
        assertThatThrownBy(()->underTest.carsYearFromTo(min, max))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Maximum year cannot be negative.");
    }

    @Test
    void carsYearFromTo_ShouldReturnException_WhenMinYearIsNegative() {
        //Given
        int min = -300;
        int max = 200;

        //When && Then
        assertThatThrownBy(()->underTest.carsYearFromTo(min, max))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Minimum year cannot be negative.");
    }


    @Test
    void carsModel() {
        //Given
        String model = "E-Class";
        Car car = new Car("MRS", model, "Blue", 250, 2020, 20000,1);
        List<Car> cars = List.of(car);

        when(carRepository.findByModel(model)).thenReturn(cars);

        //When
        List <Car> result = underTest.carsModel(model);

        //Then
        verify(carRepository).findByModel(model);
        assertThat(result).isEqualTo(cars);
    }

    @Test
    void carsYear() {
        //Given
        int year = 2020;
        Car car = new Car("MRS", "E-Class", "Blue", 250, 2020, 20000,1);
        List<Car> cars = List.of(car);

        when(carRepository.findByYear(year)).thenReturn(cars);

        //When
        List<Car> result = underTest.carsYear(year);

        //Then
        verify(carRepository).findByYear(year);
        assertThat(result).isEqualTo(cars);
    }

    @Test
    void addNewCar_ShouldAddCar() {
        //Given
        Car car = new Car("MRS", "E-Class", "Blue", 250, 2020, 20000,1);

        //When
        underTest.addNewCar(car);

        //Then
        ArgumentCaptor<Car> carArgumentCaptor = ArgumentCaptor.forClass(Car.class);

        verify(carRepository).save(carArgumentCaptor.capture());

        Car capturedCar = carArgumentCaptor.getValue();

        assertThat(capturedCar).isEqualTo(car);
    }

    @Test
    void addNewHuman_ShouldThrowException_WhenHumanIsNull() {
        // Given
        Car car = null;

        // When & Then
        AssertionsForClassTypes.assertThatThrownBy(() -> underTest.addNewCar(car))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Car object cannot be null");

        verify(humanRepository, never()).save(any());
    }

    @Test
    void deleteCarById_ShouldDeleteCarById() {
        //Given
        UUID carID = UUID.randomUUID();
        UUID humanID = UUID.randomUUID();
        Human human = new Human("Lambert", 200);
        Car car = new Car("MRS", "E-Class", "Blue", 250, 2020, 20000,1);
        car.setOwner(human);

        //Тут рефлексія
        try{
            Field idField = Human.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(human, humanID);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Cannot set ID for Human", e);
        }

        when(carRepository.findById(carID)).thenReturn(Optional.of(car));
        when(humanRepository.findById(humanID)).thenReturn(Optional.of(human));

        //When
        underTest.deleteCarById(carID);

        //Then
        verify(carRepository).deleteById(carID);
        verify(humanRepository).save(human);
        assertThat(human.getMoney()).isEqualTo(450);
    }

    @Test
    void updateAllCar() {
        //given
        UUID carID = UUID.randomUUID();
        Car car = new Car("MRS", "E-Class", "Blue", 250, 2020, 20000,1);
        Car carDetails = new Car("MRS_1", "E-Clas_1", "Blue_1", 2500, 20200, 200000,10);

        when(carRepository.findById(carID)).thenReturn(Optional.of(car));

        //When
        underTest.updateAllCar(carID, carDetails);

        //then
        assertThat(car.getBrand()).isEqualTo(carDetails.getBrand());
        assertThat(car.getModel()).isEqualTo(carDetails.getModel());
        assertThat(car.getColor()).isEqualTo(carDetails.getColor());
        assertThat(car.getPrice()).isEqualTo(carDetails.getPrice());
        assertThat(car.getYear()).isEqualTo(carDetails.getYear());
        assertThat(car.getMileage()).isEqualTo(carDetails.getMileage());
        assertThat(car.getNumberOfOwners()).isEqualTo(carDetails.getNumberOfOwners());
        verify(carRepository).save(car);
    }

    @Test
    void updateCar_updatesAllFieldsCorrectly() {
        //Given
        UUID carID = UUID.randomUUID();
        Car car = new Car("MRS", "E-Class", "Blue", 250, 2020, 20000,1);

        when(carRepository.findById(carID)).thenReturn(Optional.of(car));

        Map<String, Object> updates = Map.of(
                "Brand", "Toyota",
                "Model", "Corolla",
                "Color", "Red",
                "Price", 300,
                "Year", 2022,
                "Mileage", 15000,
                "NumberOfOwners", 2
        );

        //When
        underTest.updateCar(carID, updates);

        //Then
        assertThat(car.getBrand()).isEqualTo("Toyota");
        assertThat(car.getModel()).isEqualTo("Corolla");
        assertThat(car.getColor()).isEqualTo("Red");
        assertThat(car.getPrice()).isEqualTo(300);
        assertThat(car.getYear()).isEqualTo(2022);
        assertThat(car.getMileage()).isEqualTo(15000);
        assertThat(car.getNumberOfOwners()).isEqualTo(2);

        verify(carRepository).save(car);

    }

    @Test
    void updateCar_updatesPartialFieldsCorrectly() {
        //Given
        UUID carID = UUID.randomUUID();
        Car car = new Car("MRS", "E-Class", "Blue", 250, 2020, 20000,1);

        when(carRepository.findById(carID)).thenReturn(Optional.of(car));

        Map<String, Object> updates = Map.of(
                "Brand", "Toyota",
                "Model", "Corolla",
                "Color", "Red",
                "Year", 2022,
                "Mileage", 15000
        );

        //When
        underTest.updateCar(carID, updates);

        //Then
        assertThat(car.getBrand()).isEqualTo("Toyota");
        assertThat(car.getModel()).isEqualTo("Corolla");
        assertThat(car.getColor()).isEqualTo("Red");
        assertThat(car.getPrice()).isEqualTo(250);
        assertThat(car.getYear()).isEqualTo(2022);
        assertThat(car.getMileage()).isEqualTo(15000);
        assertThat(car.getNumberOfOwners()).isEqualTo(1);

        verify(carRepository).save(car);

    }
    @Test
    void updateCar_noUpdatesWhenNoFieldsProvided() {
        //Given
        UUID carID = UUID.randomUUID();
        Car car = new Car("MRS", "E-Class", "Blue", 250, 2020, 20000,1);

        when(carRepository.findById(carID)).thenReturn(Optional.of(car));

        Map<String, Object> updates = Map.of();

        //When
        underTest.updateCar(carID, updates);

        //Then
        assertThat(car.getBrand()).isEqualTo("MRS");
        assertThat(car.getModel()).isEqualTo("E-Class");
        assertThat(car.getColor()).isEqualTo("Blue");
        assertThat(car.getPrice()).isEqualTo(250);
        assertThat(car.getYear()).isEqualTo(2020);
        assertThat(car.getMileage()).isEqualTo(20000);
        assertThat(car.getNumberOfOwners()).isEqualTo(1);

        verify(carRepository).save(car);

    }
}