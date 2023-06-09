<template>
  <div class="row">
    <h1 style="margin-top: 16px">Создание комнат</h1>
    <div class="form-wrapper-1">
      <label for="hostel-select">Выберите общежитие</label>
      <select id="hostel-select" class="form-select" v-model="selectedHostelName">
        <option v-for="hostel in hostels" v-bind:key="hostel.id">
          {{ hostel.name }}
        </option>
      </select>
      <form ref="anyName" >
        <label for="count" style="margin-right: 16px">Количество комнат</label>
        <input type="number" class="form-control" id="count" v-model="count" placeholder="Кол-во комнат" required
               min="1"/>
        <label for="countPlaces">Количество мест</label>
        <input type="number" class="form-control" id="countPlaces" placeholder="Кол-во мест" v-model="countPlaces"
               required min="1">
        <label for="floor">Этаж</label>
        <input type="number" class="form-control" id="floor" placeholder="Этаж" v-model="floor"
               required>
        <label for="isMale">Мужская комната</label>
        <input style="margin-left: 16px" type="checkbox" id="isMale" v-model="isMale">
        <button style="margin-top: 16px;" type="submit" @click="createRoom" class="btn btn-outline-primary">Добавить
          комнаты
        </button>
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
input {
  max-width: 300px;
}
</style>
<script>
import axios from "axios";

export default {
  name: 'create-rooms-component',
  data() {
    return {
      count: '',
      selectedHostelName: '',
      selectedHostelId: -1,
      hostelId: '',
      countPlaces: '',
      floor: '',
      isMale: false,
      hostels: []
    }
  },
  mounted() {
    this.getAllHostels();
  },
  watch: {
    selectedHostelName(newVal) {
      // Find the hoster with the selected name
      const selectedHostel = this.hostels.find(h => h.name === newVal)
      console.log(selectedHostel)
      // Get the id of the selected hostel, or -1 if not found
      this.selectedHostelId = selectedHostel ? selectedHostel.id : -1
    },
  },
  methods: {
    createRoom() {
      const room = {
        name: this.name,
        countPlaces: this.countPlaces,
        floor: this.floor,
        isMale: this.isMale
      };
      let user = JSON.parse(localStorage.getItem('user'))
      axios({
        url: `http://localhost:8081/api/v1/hostel/${this.selectedHostelId}/create-rooms?count=${this.count}`,
        method: 'POST',
        data: room,
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
          'Content-Type': 'application/json'
        },
      })
          .then(response => {
            console.log(response.data);
            this.$refs.anyName.reset();
          })
          .catch(error => {
            console.log(error.response.data);
          });
    },
    getAllHostels() {
      let user = JSON.parse(localStorage.getItem('user'))
      axios({
        url: 'http://localhost:8081/api/v1/hostel/all',
        method: 'GET',
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
          'Content-Type': 'application/json'
        }
      })
          .then(response => {
            this.hostels = response.data;
          })
          .catch(error => {
            console.log(error);
          });
    }
  }
}
</script>