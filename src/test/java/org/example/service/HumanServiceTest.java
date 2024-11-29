package org.example.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;
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

@ExtendWith(MockitoExtension.class)
class HumanServiceTest {

    @Mock private HumanRepository humanRepository;
    @Mock private CarRepository carRepository;

    @InjectMocks private HumanService underTest;

    @Test
    void getAllHumans_ShouldGetAllHumans() {
        //When
        underTest.getAllHumans();
        //then
        verify(humanRepository).findAll();
    }

    @Test
    void getHumanByID_ShouldReturnHuman_WhenIDIsValid() {
        //Given
        UUID id = UUID.randomUUID();
        Human human = new Human("Lambert", 200);
        when(humanRepository.findById(id)).thenReturn(Optional.of(human));
        //When
        Human result = underTest.getHumanByID(id);
        //then
        assertThat(result).isEqualTo(human);
        verify(humanRepository).findById(id);
    }

    @Test
    void getHumanByID_ShouldReturnNull_WhenIDIsNotFound() {
        //given
        UUID id = UUID.randomUUID();
        Human human = new Human("Lambert", 200);
        when(humanRepository.findById(id)).thenReturn(Optional.of(human));

        //When
        Human result = underTest.getHumanByID(id);

        //then
        assertThat(result).isEqualTo(human);
        verify(humanRepository).findById(id);
    }

    @Test
    void getHumanByName_ShouldReturnListOfHumans_WhenNameExists() {
        //Given
        String name = "Lambert";
        Human human = new Human(name, 200);
        List<Human> humans = List.of(human);
        when(humanRepository.findHumansByName(name)).thenReturn(humans);

        //When
        List<Human> result = underTest.getByName(name);

        //Then
        verify(humanRepository).findByUsername(name);
        assertThat(result).isEqualTo(humans);
    }

    @Test
    void getHumanByName_ShouldReturnEmptyList_WhenNameDoesNotExist() {
        //Given
        String name = "NonExistentName";
        List<Human> emptyList = List.of();  // Порожній список
        when(humanRepository.findHumansByName(name)).thenReturn(emptyList);

        //When
        List<Human> result = underTest.getByName(name);

        //Then
        verify(humanRepository).findByUsername(name);
        assertThat(result).isEmpty();
    }

    @Test
    void addNewHuman_ShouldAddHuman_WhenHumanNotNull() {
        //Given
        Human human = new Human("Lambert", 200);

        //When
        underTest.addNewHuman(human);

        //Then
        ArgumentCaptor<Human> humanArgumentCaptor = ArgumentCaptor.forClass(Human.class);

        verify(humanRepository).save(humanArgumentCaptor.capture());

        Human capturedHuman = humanArgumentCaptor.getValue();

        assertThat(capturedHuman).isEqualTo(human);
    }

    @Test
    void addNewHuman_ShouldThrowException_WhenHumanIsNull() {
        // Given
        Human human = null;

        // When & Then
        assertThatThrownBy(() -> underTest.addNewHuman(human))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Human object cannot be null");

        verify(humanRepository, never()).save(any());
    }

    @Test
    void deleteHumanById_ShouldDeleteHuman_WhenIDIsValid() {
        //Given
        UUID id = UUID.randomUUID();
        when(humanRepository.existsById(id)).thenReturn(true);

        //When
        underTest.deleteHumanById(id);

        //Then
        verify(humanRepository).deleteById(id);
    }

    @Test
    void deleteHumanById_ShouldDeleteHuman_WhenIDIsNotFound() {
        //Given
        UUID id = UUID.randomUUID();
        when(humanRepository.existsById(id)).thenReturn(false);

        //When & Then
        assertThatThrownBy(()-> underTest.deleteHumanById(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Human with id " + id + " does not exist");
    }

    @Test
    void updateHuman_ShouldUpdateHuman_WhenValidUpdates() {
        //Given
        UUID humanID = UUID.randomUUID();
        Human human = new Human("Lambert", 200);
        when(humanRepository.findById(humanID)).thenReturn(Optional.of(human));

        Map<String, Object> updates = Map.of("name", "newName", "money", 300);

        //When
        underTest.updateHuman(humanID, updates);

        //then
        ArgumentCaptor<Human> humanArgumentCaptor = ArgumentCaptor.forClass(Human.class);
        verify(humanRepository).save(humanArgumentCaptor.capture());

        Human capturedHuman = humanArgumentCaptor.getValue();

        assertThat(capturedHuman.getName()).isEqualTo("newName");
        assertThat(capturedHuman.getMoney()).isEqualTo(300);
    }

    @Test
    void updateHuman_ShouldUpdateOnlyName_WhenPartialUpdates() {
        //Given
        UUID humanID = UUID.randomUUID();
        Human human = new Human("Lambert", 200);
        when(humanRepository.findById(humanID)).thenReturn(Optional.of(human));

        Map<String, Object> updates = Map.of("name", "newName");

        //When
        underTest.updateHuman(humanID, updates);

        //then
        ArgumentCaptor<Human> humanArgumentCaptor = ArgumentCaptor.forClass(Human.class);
        verify(humanRepository).save(humanArgumentCaptor.capture());

        Human capturedHuman = humanArgumentCaptor.getValue();

        assertThat(capturedHuman.getName()).isEqualTo("newName");
    }

    @Test
    void updateHuman_ShouldUpdateOnlyMoney_WhenPartialUpdates() {
        //Given
        UUID humanID = UUID.randomUUID();
        Human human = new Human("Lambert", 200);
        when(humanRepository.findById(humanID)).thenReturn(Optional.of(human));

        Map<String, Object> updates = Map.of("money", 300);

        //When
        underTest.updateHuman(humanID, updates);

        //then
        ArgumentCaptor<Human> humanArgumentCaptor = ArgumentCaptor.forClass(Human.class);
        verify(humanRepository).save(humanArgumentCaptor.capture());

        Human capturedHuman = humanArgumentCaptor.getValue();

        assertThat(capturedHuman.getMoney()).isEqualTo(300);
    }

    @Test
    void updateHuman_ShouldNotChangeAnything_WhenNoUpdatesProvided() {
        //Given
        UUID humanID = UUID.randomUUID();
        Human human = new Human("Lambert", 200);
        when(humanRepository.findById(humanID)).thenReturn(Optional.of(human));

        Map<String, Object> updates = Map.of();

        //When
        underTest.updateHuman(humanID, updates);

        //then
        ArgumentCaptor<Human> humanArgumentCaptor = ArgumentCaptor.forClass(Human.class);
        verify(humanRepository).save(humanArgumentCaptor.capture());

        Human capturedHuman = humanArgumentCaptor.getValue();

        assertThat(capturedHuman.getName()).isEqualTo("Lambert");
        assertThat(capturedHuman.getMoney()).isEqualTo(200);
    }

    @Test
    void updateAllHuman_ShouldUpdateHuman() {
        //Given
        UUID humanID = UUID.randomUUID();
        Human human = new Human("Lambert", 200);
        when(humanRepository.findById(humanID)).thenReturn(Optional.of(human));

        Human updateHuman = new Human("LambertNew", 300);

        //When
        underTest.updateAllHuman(humanID, updateHuman);

        //Then
        ArgumentCaptor<Human> humanArgumentCaptor = ArgumentCaptor.forClass(Human.class);
        verify(humanRepository).save(humanArgumentCaptor.capture());

        Human capturedHuman = humanArgumentCaptor.getValue();

        assertThat(capturedHuman.getMoney()).isEqualTo(300);
        assertThat(capturedHuman.getName()).isEqualTo("LambertNew");
    }

    @Test
    void updateAllHuman_ShouldUpdateEverythingToNull_WhenNoUpdatesProvided() {
        //Given
        UUID humanID = UUID.randomUUID();
        Human human = new Human("Lambert", 200);
        when(humanRepository.findById(humanID)).thenReturn(Optional.of(human));

        Human updateHuman = new Human();

        //When
        underTest.updateAllHuman(humanID, updateHuman);

        //Then
        ArgumentCaptor<Human> humanArgumentCaptor = ArgumentCaptor.forClass(Human.class);
        verify(humanRepository).save(humanArgumentCaptor.capture());

        Human capturedHuman = humanArgumentCaptor.getValue();

        assertThat(capturedHuman.getMoney()).isEqualTo(0);
        assertThat(capturedHuman.getName()).isEqualTo(null);
    }

    @Test
    void purchaseCar_HumanBuyCar() {
        //Given
        UUID humanID = UUID.randomUUID();
        UUID carID = UUID.randomUUID();

        Human human = new Human("Lambert", 1000);
        Car car = new Car("MRS", "E-Class", "Blue", 800, 2020, 20000,1);

        when(humanRepository.findById(humanID)).thenReturn(Optional.of(human));
        when(carRepository.findById(carID)).thenReturn(Optional.of(car));

        //When
        underTest.purchaseCar(humanID, carID);

        //Then
        assertThat(human.getMoney()).isEqualTo(200);
        assertThat(human.getHumanCars()).hasSize(1);
        assertThat(human.getHumanCars()).contains(car);
        assertThat(car.getOwner()).isEqualTo(human);
        assertThat(car.getNumberOfOwners()).isEqualTo(2);

        verify(humanRepository).save(human);
        verify(carRepository).save(car);

    }

    @Test
    void purchaseCar_shouldThrowExceptionWhenNotEnoughMoneyForPurchase() {
        //Given
        UUID humanID = UUID.randomUUID();
        UUID carID = UUID.randomUUID();

        Human human = new Human("Lambert", 300);
        Car car = new Car("MRS", "E-Class", "Blue", 800, 2020, 20000,1);

        when(humanRepository.findById(humanID)).thenReturn(Optional.of(human));
        when(carRepository.findById(carID)).thenReturn(Optional.of(car));

        //When & Then
        assertThatThrownBy(()-> underTest.purchaseCar(humanID, carID))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(human.getName() + " you don't have enough money to buy a car");

    }

    @Test
    void purchaseCar_shouldThrowExceptionWhenCarDoesNotExist() {
        //Given
        UUID humanID = UUID.randomUUID();
        UUID carID = UUID.randomUUID();

        Human human = new Human("Lambert", 200);
        when(humanRepository.findById(humanID)).thenReturn(Optional.of(human));
        when(carRepository.findById(carID)).thenReturn(Optional.empty());

        //When & Then
        assertThatThrownBy(()-> underTest.purchaseCar(humanID, carID))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Car no found with this id: " + carID);

    }

    @Test
    void purchaseCar_shouldThrowExceptionWhenCarHaveOwnerAlready() {
        //Given
        UUID humanID = UUID.randomUUID();
        UUID previousOwnerID = UUID.randomUUID();
        UUID carID = UUID.randomUUID();

        Human human = new Human("Lambert", 200);
        Human previousOwner = new Human("Mitch", 500);

        try{
            Field idField = Human.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(previousOwner, previousOwnerID);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
             throw new RuntimeException("Cannot set ID for Human", e);
        }

        Car car = new Car("MRS", "E-Class", "Blue", 800, 2020, 20000,1);
        car.setOwner(previousOwner);

        when(humanRepository.findById(humanID)).thenReturn(Optional.of(human));
        when(carRepository.findById(carID)).thenReturn(Optional.of(car));

        //When & Then
        assertThat(car.getOwner()).isEqualTo(previousOwner);
        assertThatThrownBy(()-> underTest.purchaseCar(humanID, carID))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("This car already has an owner");

        verify(carRepository, never()).save(any(Car.class));
        verify(humanRepository, never()).save(any(Human.class));
    }

    @Test
    void sellCar_ShouldSellCar(){
        //Given
        UUID humanID = UUID.randomUUID();
        UUID carID = UUID.randomUUID();

        Human human = new Human("Lambert", 200);
        Car car = new Car();

        try{
            Field idField = Human.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(human, humanID);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Cannot set ID for Human", e);
        }

        car.setOwner(human);

        when(humanRepository.findById(humanID)).thenReturn(Optional.of(human));
        when(carRepository.findById(carID)).thenReturn(Optional.of(car));

        //When
        underTest.sellCar(humanID, carID);

        //Then
        assertThat(human.getHumanCars()).doesNotContain(car);
        assertThat(car.getOwner()).isEqualTo(null);

        verify(humanRepository).save(human);
        verify(carRepository).save(car);
    }

    @Test
    void sellCar_ShouldThrowExceptionWhenCarDoesNotBelongToHuman(){
        //Given
        UUID humanID = UUID.randomUUID();
        UUID carID = UUID.randomUUID();

        Human human = new Human("Lambert", 200);
        Car car = new Car();

        when(humanRepository.findById(humanID)).thenReturn(Optional.of(human));
        when(carRepository.findById(carID)).thenReturn(Optional.of(car));

        //When & Then
        assertThatThrownBy(() -> underTest.sellCar(humanID, carID))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("this car does not belong to this person");

        verify(humanRepository, never()).save(any(Human.class));
        verify(carRepository, never()).save(any(Car.class));
    }
}