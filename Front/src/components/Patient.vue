<!-- eslint-disable prettier/prettier -->
<script setup lang="ts">
import type { Patient } from "@/models/Patient";
import { onBeforeMount, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import Datepicker from "@vuepic/vue-datepicker";
import "@vuepic/vue-datepicker/dist/main.css";
import EventService from "@/services/EventService";
import Alerts from "@/utils/Alerts";

const router = useRoute();
const routerLink = useRouter();

const patient = ref({} as Patient);
const valid = false;

const nameRules = [
  (v) => !!v || "Se requiere el nombre",
  (v) =>
    (v && v.length <= 100) || "El nombre debe tener menos de 100 caracteres",
];
const lastNameRules = [
  (v) => !!v || "Se requiere el apellido",
  (v) =>
    (v && v.length <= 100) || "El apellido debe tener menos de 100 caracteres",
];
const dateBithRules = [(v) => !!v || "Se requiere la fecha de nacimiento"];
const genderRules = [(v) => !!v || "Se requiere el genero"];

const form = ref(null);
const validDate = ref(false);
const title = ref("");
const isUpdate = ref(false);
const btnEnabled = ref(false);

onBeforeMount(() => {
  const id = router.params.id;
  if (id) {
    loadPatient(Number.parseInt(id));
  } else {
    title.value = "CREAR PACIENTE";
  }
});

const loadPatient = async (id: number) => {
  patient.value = await EventService.getPatient(id).then(
    (res) => {
      title.value = "EDITAR PACIENTE";
      isUpdate.value = true;
      return res.data;
    },
    (err) => {
      Alerts.showMessage(
        "error",
        err.response.data.message,
        1500,
        "top-center"
      );
      navigateTo("home");
      return ref({} as Patient);
    }
  );
};

const navigateTo = (path: string) => {
  routerLink.push({ name: path });
};

const validate = async () => {
  if (form.value) {
    const { valid } = await form.value.validate();
    if (!valid || validDate.value) {
      return;
    }
    btnEnabled.value = true;    
    isUpdate.value ? updateUser() : saveUser();
  }
};

const typeGender = [
  { name: "Femenino", value: "FEMALE" },
  { name: "Masculino", value: "MALE" },
];

const saveUser = () => {
  EventService.savePatient(patient.value).then(
    (res) => {
      Alerts.showMessage(
        "success",
        "Usuario creado con éxito",
        2000,
        "top-center"
      );
      navigateTo("home");
    },
    (err) => {
      showErrorMessage(err.response.data.message);
    }
  );
};

const updateUser = () => {
  EventService.updatePatient(patient.value).then(
    (res) => {
      Alerts.showMessage(
        "success",
        "Usuario actualizado con éxito",
        2000,
        "top-center"
      );
      navigateTo("home");
    },
    (err) => {
      showErrorMessage(err.response.data.message);
    }
  );
};

const showErrorMessage = (message: string) => {
  btnEnabled.value = false;
  Alerts.showErrorMessage(message);
};
</script>

<template>
  <div class="box-container">
    <v-col cols="12" md="12">
      <v-btn
        prepend-icon="mdi-home-circle"
        elevation="2"
        large
        x-large
        @click="navigateTo('home')"
      >
        Inicio</v-btn
      >
    </v-col>
    <v-form ref="form" v-model="valid">
      <v-container>
        <v-row>
          <v-col cols="12" md="12">
            <h1 class="alert alert-dark text-center">{{ title }}</h1>
          </v-col>
          <v-col cols="12" md="12">
            <v-text-field
              v-model="patient.name"
              variant="solo"
              :rules="nameRules"
              :counter="100"
              label="Nombre"
              required
            >
            </v-text-field>
          </v-col>

          <v-col cols="12" md="12">
            <v-text-field
              v-model="patient.lastName"
              variant="solo"
              :counter="100"
              :rules="lastNameRules"
              label="Apellido"
              required
            ></v-text-field>
          </v-col>

          <v-col cols="12" md="12">
            <v-select
              v-model="patient.gender"
              :items="typeGender"
              item-title="name"
              item-value="value"
              label="Genero"
              variant="solo"
              persistent-hint
              single-line
              required
              :rules="genderRules"
            ></v-select>
          </v-col>

          <v-col cols="12" md="12">
            <Datepicker
              input-class-name="box-date-picker"
              v-model="patient.birthDate"
              vertical
              :enable-time-picker="false"
              :max-date="new Date()"
            ></Datepicker>
            <div v-if="validDate" class="box-error">
              <p class="string-error">Se requiere la fecha</p>
            </div>
          </v-col>
        </v-row>
      </v-container>
    </v-form>
    <div class="text-center">
      <v-btn
        color="success"
        size="x-large"
        class="me-4"
        @click="validate"
        :disabled="btnEnabled"
      >
        {{ isUpdate ? "Actualizar Paciente" : "Crear Paciente" }}
      </v-btn>
    </div>
  </div>
</template>

<style>
.event-link {
  color: black;
  text-decoration: none;
  font-weight: 100;
}
.string-error {
  font-size: 12px;
  margin-left: 15px;
  margin-top: 5px;
}

.box-error {
  border-top: 1px solid #b02a37;
}

.box-container {
  margin: auto;
  width: 80%;
}

.box-date-picker {
  height: 50px;
  box-shadow: 1px 1px 2px 1px rgb(0 0 0 / 20%);
}
</style>
