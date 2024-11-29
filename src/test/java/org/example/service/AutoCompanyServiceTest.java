package org.example.service;

import org.example.models.AutoCompany;
import org.example.models.Car;
import org.example.models.Human;
import org.example.repository.AutoCompaniesRepository;
import org.example.repository.CarRepository;
import org.example.repository.HumanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutoCompanyServiceTest {

    @Mock private AutoCompaniesRepository autoCompaniesRepository;
    @Mock private CarRepository carRepository;
    @Mock private HumanRepository humanRepository;

    @InjectMocks private AutoCompanyService underTest;

    @Test
    void getAutoCompanyByID_ShouldGetAutoCompanyByID() {
        //Given
        UUID autoCompanyID = UUID.randomUUID();
        AutoCompany autoCompany = new AutoCompany("VAG");

        when(autoCompaniesRepository.findById(autoCompanyID)).thenReturn(Optional.of(autoCompany));

        //When
        underTest.getAutoCompanyByID(autoCompanyID);

        //Then
        verify(autoCompaniesRepository).findById(autoCompanyID);
    }

    @Test
    void getAutoCompanyByID_ShouldReturnException_WhenAutoCompanyNotFound() {
        //Given
        UUID autoCompanyID = UUID.randomUUID();

        //When && Then
        assertThatThrownBy(()-> underTest.getAutoCompanyByID(autoCompanyID))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("AutoCompany not found with id " + autoCompanyID);
    }

    @Test
    void getAutoCompany() {
        //When
        underTest.getAutoCompany();

        //Then
        verify(autoCompaniesRepository).findAll();
    }

    @Test
    void addAutoCompany() {
        //Given
        AutoCompany autoCompany = new AutoCompany("VAG");

        //When
        underTest.addAutoCompany(autoCompany);

        //Then
        verify(autoCompaniesRepository).save(autoCompany);
    }

    @Test
    void deleteAutoCompany_ShouldDeleteAutoCompany() {
        //Given
        UUID autoCompanyID = UUID.randomUUID();
        when(autoCompaniesRepository.existsById(autoCompanyID)).thenReturn(true);

        //When
        underTest.deleteAutoCompany(autoCompanyID);

        //Then
        verify(autoCompaniesRepository).deleteById(autoCompanyID);
    }

    @Test
    void deleteAutoCompanyById_ShouldDeleteAutoCompany_WhenIDIsNotFound() {
        //Given
        UUID autoCompanyID = UUID.randomUUID();
        when(autoCompaniesRepository.existsById(autoCompanyID)).thenReturn(false);

        //When & Then
        assertThatThrownBy(()-> underTest.deleteAutoCompany(autoCompanyID))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("AutoCompany with id " + autoCompanyID + " does not exist");
    }

    @Test
    void buyCar_AutoCompanyBuyCar_WhenCarHaveOwnerHuman() {
        //Given
        UUID autoCompanyID = UUID.randomUUID();
        UUID humanID = UUID.randomUUID();
        UUID carID = UUID.randomUUID();
        AutoCompany autoCompany = new AutoCompany("VAG");
        Human human = new Human("Lambert", 200);
        Car car = new Car("MRS", "E-Class", "Blue", 800, 2020, 20000,1);
        car.setOwner(human);

        when(humanRepository.findById(humanID)).thenReturn(Optional.of(human));
        when(autoCompaniesRepository.findById(autoCompanyID)).thenReturn(Optional.of(autoCompany));
        when(carRepository.findById(carID)).thenReturn(Optional.of(car));

        //Тут рефлексія
        try{
            Field idField = Human.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(human, humanID);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Cannot set ID for Human", e);
        }

        //When
        underTest.buyCar(autoCompanyID, carID);

        //Then
        assertThat(autoCompany.getAutoCompanyCars()).contains(car);
        assertThat(human.getHumanCars()).doesNotContain(car);
        assertThat(car.getOwner()).isEqualTo(null);
        assertThat(car.getAutoCompany()).isEqualTo(autoCompany);

        verify(autoCompaniesRepository).save(autoCompany);
        verify(humanRepository).save(human);
        verify(carRepository).save(car);
    }

    @Test
    void buyCar_autoCompanyShouldBuyCar_whenCarHasNoOwner() {
        //Given
        UUID autoCompanyID = UUID.randomUUID();
        UUID carID = UUID.randomUUID();
        AutoCompany autoCompany = new AutoCompany("VAG");
        Car car = new Car("MRS", "E-Class", "Blue", 800, 2020, 20000,1);

        when(autoCompaniesRepository.findById(autoCompanyID)).thenReturn(Optional.of(autoCompany));
        when(carRepository.findById(carID)).thenReturn(Optional.of(car));

        //When
        underTest.buyCar(autoCompanyID, carID);

        //Then
        assertThat(autoCompany.getAutoCompanyCars()).contains(car);
        assertThat(car.getAutoCompany()).isEqualTo(autoCompany);

        verify(autoCompaniesRepository).save(autoCompany);
        verify(carRepository).save(car);
    }

    @Test
    void buyCar_ShouldReturnException_WhenCarHaveAutoCompany() {
        //Given
        UUID autoCompanyID = UUID.randomUUID();
        UUID carID = UUID.randomUUID();
        AutoCompany autoCompany = new AutoCompany("VAG");
        Car car = new Car("MRS", "E-Class", "Blue", 800, 2020, 20000,1);
        car.setAutoCompany(autoCompany);

        when(autoCompaniesRepository.findById(autoCompanyID)).thenReturn(Optional.of(autoCompany));
        when(carRepository.findById(carID)).thenReturn(Optional.of(car));

        //Тут рефлексія
        try{
            Field idField = AutoCompany.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(autoCompany, autoCompanyID);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Cannot set ID for AutoCompany", e);
        }

        //When
        assertThatThrownBy(()-> underTest.buyCar(autoCompanyID, carID))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("the car is the property of the car company");

    }

}