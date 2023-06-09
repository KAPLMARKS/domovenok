<template>
  <nav-bar></nav-bar>
  <h1 style="margin-top: 16px; text-align: center">Жилой фонд</h1>
  <div>
    <ul class="nav justify-content-center">
    <button id="1" class="nav-link netnet" :class="{ 'active': showComponent === 1 }" @click="toggleComponent(1)">Создание зданий</button>
    <button id="2" class="nav-link netnet" :class="{ 'active': showComponent === 2 }" @click="toggleComponent(2)">Cоздание комнат</button>
    <button id="3" class="nav-link netnet" :class="{ 'active': showComponent === 3 }" @click="toggleComponent(3)">Список общежитий</button>
    </ul>

    <div v-if="showComponent === 1"><create-hostel></create-hostel></div>
    <div v-if="showComponent === 2"><create-rooms></create-rooms></div>
    <div v-if="showComponent === 3"><list-fond></list-fond></div>
  </div>
</template>
<style>
.netnet {
  border-radius: 20px; /* adjust the value to change the oval shape */
  border:none;
  padding: 10px 20px; /* adjust the values to change the button size */
  font-size: 16px;
  font-weight: bold;
  background-color: white;
  cursor: pointer;
}
.active {
  color: white !important;
  background-color: #009688 !important;
}
</style>
<script>
import Header from "@/components/components/Header";
import CreateRooms from "@/components/components/CreateRooms";
import CreateHostel from "@/components/components/CreateHostel";
import ListFond from "@/components/components/ListFond";

export default {
  name: 'fond-page',
  components: {
    "nav-bar": Header,
    "create-hostel": CreateHostel,
    "create-rooms": CreateRooms,
    "list-fond": ListFond
  },
  data() {
    return {
      showComponent: 1
    }
  },
  methods: {
    toggleComponent(id) {
      if (id === 1) {
        this.showComponent = 1
      } else if (id === 2) {
        this.showComponent = 2
      } else if (id === 3) {
        this.showComponent = 3
      }
    }
  }
}
</script>