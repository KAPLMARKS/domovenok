<template>
  <div class="row">
    <h1 style="margin-top: 16px;">Создание зданий</h1>
    <div class="form-items">
      <form>
        <div class="form-wrapper-1">
          <label for="name">Имя здания</label>
          <input type="text" class="form-control" id="name" placeholder="Имя" v-model="name" required>
          <label for="countFloors">Количество этажей</label>
          <input type="number" class="form-control" id="countFloors" placeholder="Кол-во этажей" v-model="countFloors"
                 required>
          <label for="countRooms">Количество комнат</label>
          <input type="number" class="form-control" id="countRooms" placeholder="Кол-во комнат" v-model="countRooms"
                 required>
          <label for="countPlaces">Количество мест</label>
          <input type="number" class="form-control" id="countPlaces" placeholder="Кол-во мест" v-model="countPlaces"
                 required>
        <button style="margin-top: 16px;" type="submit" @click="createHostel" class="btn btn-outline-primary">Добавить
          здание
        </button>
        </div>
      </form>
    </div>
  </div>
</template>
<style>
.active {
  color: darkgreen;
  border-radius: 20px; /* adjust the value to change the oval shape */
  border: 2px solid green;
  padding: 10px 20px; /* adjust the values to change the button size */
  font-size: 16px;
  font-weight: bold;
  background-color: white;
  cursor: pointer;
}
</style>
<script>
import axios from "axios";

export default {
  name: 'create-hostel-component',
  data() {
    return {
      name: '',
      countFloors: '',
      countRooms: '',
      countPlaces: ''
    }
  },
  methods: {
    createHostel() {
      const hostel = {
        name: this.name,
        countFloors: this.countFloors,
        countRooms: this.countRooms,
        countPlaces: this.countPlaces,
      };
      let user = JSON.parse(localStorage.getItem('user'))
      axios({
        url: 'http://localhost:8081/api/v1/hostel',
        method: 'POST',
        data: hostel,
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
          'Content-Type': 'application/json'
        },
      })
          .then(response => {
            console.log(response.data);
            window.location.reload();
            this.$refs.anyName.reset();
          })
          .catch(error => {
            console.log(error.response.data);
          });
    }
  }
}
</script>