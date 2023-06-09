<template>
  <nav-bar></nav-bar>
  <div class="form-body">
    <div class="row">
      <div class="form-holder">
        <div class="form-content">
          <div class="form-items">
            <h3>Подать заявку</h3>
            <SendingImage/>
            <form class="requires-validation">

              <div class="col-md-12">
                <input class="form-control" type="email" name="email" placeholder="E-mail" v-model="application.email"
                       required>
              </div>

              <div class="col-md-12">
                <input class="form-control" type="text" name="organizationName" placeholder="Название организации"
                       v-model="application.organizationName" required>
              </div>
              <div class="form-button mt-3">
                <button @click="createApplication" id="submit" type="submit" class="button">Отправить заявку</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
*, body {
  font-family: 'Poppins', sans-serif;
  font-weight: 400;
  -webkit-font-smoothing: antialiased;
  text-rendering: optimizeLegibility;
  -moz-osx-font-smoothing: grayscale;
}


.form-holder {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  min-height: 0px !important;
}

.form-holder .form-content {
  position: relative;
  text-align: center;
  display: -webkit-box;
  display: -moz-box;
  display: -ms-flexbox;
  display: -webkit-flex;
  display: flex;
  -webkit-justify-content: center;
  justify-content: center;
  -webkit-align-items: center;
  align-items: center;
  padding: 60px;
}

.form-content .form-items {
  border: 3px solid #388E3C !important;
  border-radius: 0px !important;
  padding: 40px;
  width: 100%;
  min-width: 540px;
  -webkit-border-radius: 10px;
  -moz-border-radius: 10px;
  text-align: left;
  -webkit-transition: all 0.4s ease;
  transition: all 0.4s ease;
  display: grid !important;
  justify-content: center !important;
  flex-direction: column !important;
  align-content: center !important;
  align-items: center !important;
  justify-items: center !important;
}

.form-control {
  border: 2px solid #646464 !important;
  border-radius: 0px !important;
  height: 60px;
  font-size: 20px;
}

input[type="text"], input[type="email"] {
  font-size: 16px !important;
  font-family: 'Manrope';
}

input:focus {
  background-color: #646464 !important;
  color: white !important;
  box-shadow: none !important;
}

input:focus::-webkit-input-placeholder {
    color: white !important;
}

#submit {
  margin: 30px 0 0;
}

.requires-validation {
  margin-top: 20px;
}

.form-content h3 {
  color: #484848 !important;
  text-align: left;
  font-size: 28px;
  font-weight: 600;
  margin-bottom: 5px;
  font-family: 'Manrope' !important;
}

.form-content h3.form-title {
  margin-bottom: 30px;
}

.form-content p {
  color: #fff;
  text-align: left;
  font-size: 17px;
  font-weight: 300;
  line-height: 20px;
  margin-bottom: 30px;
}


.form-content label, .was-validated .form-check-input:invalid ~ .form-check-label, .was-validated .form-check-input:valid ~ .form-check-label {
  color: #fff;
}

.form-content input[type=text], .form-content input[type=password], .form-content input[type=email], .form-content select {
  width: 100%;
  padding: 9px 20px;
  text-align: left;
  border: 0;
  outline: 0;
  border-radius: 6px;
  background-color: #fff;
  font-size: 15px;
  font-weight: 300;
  color: #8D8D8D;
  -webkit-transition: all 0.3s ease;
  transition: all 0.3s ease;
  margin-top: 16px;
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
  /*height: 100%;*/
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

.form-content textarea {
  position: static !important;
  width: 100%;
  padding: 8px 20px;
  border-radius: 6px;
  text-align: left;
  background-color: #fff;
  border: 0;
  font-size: 15px;
  font-weight: 300;
  color: #8D8D8D;
  outline: none;
  resize: none;
  height: 120px;
  -webkit-transition: none;
  transition: none;
  margin-bottom: 14px;
}

.form-content textarea:hover, .form-content textarea:focus {
  border: 0;
  background-color: #ebeff8;
  color: #8D8D8D;
}
</style>

<script>
import RequisitionService from "@/services/RequisitionService";
import Header from "@/components/components/Header.vue";
import SendingImage from "@/components/components/assets/SendingImage.vue";

export default {
  name: "create-application",
  components: {
    SendingImage,
    "nav-bar": Header
  },
  data() {
    return {
      application: {
        email: "",
        organizationName: "",
      }
    };
  },
  methods: {
    createApplication() {
      var data = {
        email: this.application.email,
        organizationName: this.application.organizationName
      };

      RequisitionService.create(data)
          .then(response => {
            console.log(response.data);
          })
          .catch(e => {
            console.log(e);
          });
      this.$router.push('/success-page')
    },
  }
};
</script>