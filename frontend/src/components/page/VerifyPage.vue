<template>
    <div class="content">
      <h1>Почта подтверждена</h1>
      <div class="form-button mt-3">
        <router-link to="/"><button id="submit" type="submit" class="button">На главную</button></router-link>
      </div>
    </div>
</template>
<script>


import VerifyService from "@/services/VerifyService";
import {useRoute} from "vue-router";

export default {
  name: 'VerifyPage',
  setup() {
  const route = useRoute();
  var token = route.params.token;
  console.log(token);
  VerifyService.create(token)
        .then(response => {
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
  }
}
</script>

<style scoped>

.content {
  margin-left: 16px;
  width: 100%;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  text-align: center;
  color: #fff;
}

.content h1 {
  font-size: 70px;
  margin-top: 80px;
}

.content p {
  margin: 20px auto;
  font-weight: 100;
  line-height: 25px;
}

.button {
  width: 200px;
  padding: 15px 0;
  text-align: center;
  margin: 20px 10px;
  border-radius: 25px;
  font-weight: bold;
  border: 2px solid #009688;
  background: transparent;
  color: #fff;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.button:hover {
  border-radius: 0px;
  font-weight: bold;
  border: 2px solid #009688;
  background-color: #009688;
  color: #fff !important;
}
</style>