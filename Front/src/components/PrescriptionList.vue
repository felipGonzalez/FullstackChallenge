<script setup lang="ts">
import type { Patient } from "@/models/Patient";
import EventService from "@/services/EventService";
import { onBeforeMount, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import Utils from "@/utils/Utils";

const router = useRoute();
const patient = ref(null);
const messageError = ref(null);
const routerLink = useRouter();
const prescriptions = ref({});

onBeforeMount(() => {
  const id = router.params.id;
  if (id) {
    loadPatient(Number.parseInt(id));
    loadPrescriptions(Number.parseInt(id));
  } else {
    navigateTo({ name: "home" });
  }
});

const loadPatient = async (id: number) => {
  patient.value = await EventService.getPatient(id).then(
    (res) => {
      return res.data;
    },
    (err) => {
      messageError.value = err.response.data.message;
    }
  );
};

const loadPrescriptions = async (id: number) => {
  const data = await EventService.loadPrescriptionUser(id, 0, 20);
  prescriptions.value = data.data;
};

const navigateTo = (path: {}) => {
  routerLink.push(path);
};
</script>

<template>
  <v-row>
    <v-col cols="12" md="12">
      <v-btn
        prepend-icon="mdi-home-circle"
        elevation="2"
        large
        x-large
        @click="navigateTo({ name: 'home' })"
      >
        Inicio</v-btn
      >
    </v-col>
    <v-col cols="12" md="12">
      <h1 class="alert alert-dark text-center">HISTORIAL DE PRESCRIPCIONES</h1>
    </v-col>

    <v-col cols="12" md="12">
      <v-alert v-if="messageError" type="warning">{{ messageError }}</v-alert>
    </v-col>
  </v-row>

  <v-container v-if="patient">
    <v-row>
      <v-col sm="12" md="3"
        ><div class="text-overline mb-1">
          Nombre: {{ patient?.name ? patient.name : "" }}
          {{ patient?.lastName ? patient.lastName : "" }}
        </div>
      </v-col>
      <v-col sm="12" md="3">
        <div class="text-overline mb-1">
          Edad: {{ Utils.calculateAge(patient?.birthDate) }}
        </div>
      </v-col>
      <v-col sm="12" md="3">
        <div class="text-overline mb-1">
          Fecha de nacimiento: {{ patient?.birthDate }}
        </div>
      </v-col>
      <v-col sm="12" md="3">
        <div class="text-overline mb-1">
          Genero: {{ Utils.getGender(patient?.gender) }}
        </div>
      </v-col>
    </v-row>
  </v-container>

  <v-table>
    <thead>
      <tr>
        <th class="text-left">ID</th>
        <th class="text-left">Fecha de prescription</th>
        <th class="text-left">Medicamento</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="item in prescriptions.content" :key="item.name">
        <td>{{ item.id }}</td>
        <td>{{ item.datePrescription }}</td>
        <td>{{ item.medicament.name }}</td>
      </tr>
    </tbody>
  </v-table>
</template>

<style>
.event-link {
  color: black;
  text-decoration: none;
  font-weight: 100;
}
</style>
