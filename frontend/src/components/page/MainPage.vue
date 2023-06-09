<template>
  <nav-bar></nav-bar>
  <div class="content">
    <div class="main-title">
      <h1>Домовенок - легче управлять жильем для студентов!</h1>
    </div>
    <div class="content-wrapper">
      <div class="text-content">
        <div class="description">
          Наш сервис помогает облегчить работу общежития.
          <br><br>Мы предоставляем инновационные решения для управления жизнью студентов в общежитии, включая онлайн-системы бронирования, автоматизированные
          процессы проживания, а также множество других удобных инструментов.
          <br><br>Наша цель - обеспечить безопасное и
          комфортное проживание студентов, а также сэкономить время и силы персонала общежития.
          <br><br>Работая с нами, вы
          получаете надежного партнера, который поможет вам справиться со всеми задачами, связанными с управлением
          общежитием.
        </div>
      </div>
      <div class="image-content">
        <BuildingImage/>
      </div>
    </div>
    <div class="form-button mt-3" v-if="!user">
      <router-link to="/requisition">
        <button id="submit" type="submit" class="button">Подать заявку</button>
      </router-link>
    </div>
  </div>
</template>

<style>
.content {
  display: flex;
  flex-direction: column;
  margin-left: 15% !important;
  width: 75% !important;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  text-align: center;
  color: #000 !important;
}

.content-wrapper {
  display: flex;
  margin: 30px;
}

.text-content {
  width: 50%;
  display: flex;
  align-content: center;
  justify-content: center;
  align-items: center;
}

.image-content {
  width: 50%;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-content: center;
}

.content h1 {
  font-size: 50px !important;
  margin-top: 80px;
  font-family: 'Manrope';
}

.content p {
  margin: 20px auto;
  font-weight: 100;
  line-height: 25px;
  font-family: 'Manrope';
}

.description {
  font-family: 'Manrope';
}

.button {
  width: 300px !important;
  padding: 15px 0;
  text-align: center;
  margin: 20px 10px;
  border-radius: 0px !important;
  font-weight: bold;
  border: 2px solid #009688;
  background: transparent;
  color: #009688 !important;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  font-family: 'Manrope';
}

.button:hover {
  border-radius: 0px;
  font-weight: bold;
  border: 2px solid #009688;
  background-color: #009688;
  color: #fff !important;
}

</style>
<script>
import Header from "@/components/components/Header.vue";
import BuildingImage from "@/components/components/assets/BuildingImage.vue";


export default {
  name: "main-page",
  components: {
    BuildingImage,
    "nav-bar": Header
  },
  computed: {
    user() {
      return JSON.parse(localStorage.getItem('user'))
    }
  },
};
</script>