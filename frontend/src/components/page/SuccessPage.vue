<template>
  <div class="content">
    <h1>Заявка отправлена</h1>
    <div class="form-button mt-3">
      <router-link to="/">
        <button id="submit" type="submit" class="button">На главную</button>
      </router-link>
    </div>
  </div>
</template>
<style>

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

span {
  background: #009688;
  height: 100%;
  width: 0;
  border-radius: 25px;
  position: absolute;
  left: 0;
  bottom: 0;
  z-index: -1;
  transition: 0.5s
}

button:hover span {
  width: 100%;
}

button:hover {
  border: none;
}
</style>
<script>
export default {
  name: 'SuccessPage',
};
</script>