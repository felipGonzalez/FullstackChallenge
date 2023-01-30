<!-- eslint-disable prettier/prettier -->
<script setup lang="ts">
import type { Patient } from "@/models/Patient";
import type { Medicament } from "@/models/Medicament";
import EventService from "@/services/EventService";
import Alerts from "@/utils/Alerts";
import Utils from "@/utils/Utils";
import { onBeforeMount, ref } from "vue";
import { useRoute, useRouter } from "vue-router";

const router = useRoute();
const routerLink = useRouter();
const patient = ref(null);
const patients = ref([] as Patient);
const medicament = ref(null);
const medicaments = ref([] as Patient);
const valid = false;
const disableListPatient = ref(false);
const messageError = ref(null);

const prueba = (id) => {
  console.log(id);
};

onBeforeMount(() => {
  const id = router.params.id;
  if (id) {
    disableListPatient.value = true;
    loadPatient(Number.parseInt(id));
  } else {
    patient.value = undefined;
    loadPatients("");
  }
  loadMedicaments("");
});

const loadPatient = async (id: number) => {
  patient.value = await EventService.getPatient(id).then(
    (res) => {
      return res.data;
    },
    (err) => {
      Alerts.showMessage(
        "error",
        err.response.data.message,
        1500,
        "top-center"
      );
      navigateTo({name:"home"});
      return ref({} as Patient);
    }
  );
};

const loadPatients = async (text: string) => {
  patients.value = await EventService.searchUsers(text).then(
    (res) => {
      return res.data;
    },
    (err) => {
      Alerts.showMessage(
        "error",
        err.response.data.message,
        1500,
        "top-center"
      );
      navigateTo({name:"home"});
      return ref({} as Patient);
    }
  );
};

const loadMedicaments = async (text: string) => {
  medicaments.value = await EventService.searchMedicaments(text).then(
    (res) => {
      return res.data;
    },
    (err) => {
      Alerts.showMessage(
        "error",
        err.response.data.message,
        1500,
        "top-center"
      );
      navigateTo({name:"home"});
      return ref({} as Patient);
    }
  );
};

const savePrescription = async () => {
  if (patient.value == null || medicament.value == null) {
    return;
  }
  const prescription = {
    patientId: patient.value.id,
    medicamentId: medicament.value.id,
  };

  EventService.savePrescription(prescription).then(
    (res) => {
      Alerts.showMessage(
        "success",
        "Prescripción creada con éxito",
        2000,
        "top-center"
      );
      navigateTo({name: 'prescription-user', params: { id: patient?.value?.id }});
    },
    (err) => {
      messageError.value = err.response.data.message;
    }
  );
};

const navigateTo = (path: {}) => {
  routerLink.push(path);
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
        @click="navigateTo({name:'home'})"
      >
        Inicio</v-btn
      >
    </v-col>

    <v-form ref="form" v-model="valid">
      <v-container>
        <v-row>
          <v-col cols="12" md="12">
            <h1 class="alert alert-dark text-center">PRESCRIPCIONES</h1>
          </v-col>
          <v-col cols="12">
            <v-alert v-if="messageError" type="warning">{{
              messageError
            }}</v-alert>
          </v-col>
          <v-col xs="12">
            <v-card class="mx-auto" min-width="300" variant="outlined">
              <v-card-item>
                <div>
                  <div class="text-h6 mb-1">Paciente</div>
                  <div class="text-overline mb-1">
                    Nombre: {{ patient?.name ? patient.name : "" }}
                    {{ patient?.lastName ? patient.lastName : "" }}
                  </div>
                  <div class="text-overline mb-1">
                    Edad: {{ Utils.calculateAge(patient?.birthDate) }}
                  </div>
                  <div class="text-overline mb-1">
                    Fecha de nacimiento: {{ patient?.birthDate }}
                  </div>
                  <div class="text-overline mb-1">
                    Genero: {{ Utils.getGender(patient?.gender) }}
                  </div>
                </div>
              </v-card-item>
              <v-card-actions>
                <v-autocomplete
                  v-model="patient"
                  item-title="name"
                  label="Buscar Paciente"
                  :items="patients"
                  variant="solo"
                  return-object
                  :disabled="disableListPatient"
                ></v-autocomplete>
              </v-card-actions>
            </v-card>
          </v-col>
          <v-col xs="12">
            <v-card class="mx-auto" min-width="300" variant="outlined">
              <v-card-item>
                <div>
                  <div class="text-h6 mb-1">Medicamento</div>
                  <div class="text-overline mb-1">
                    Nombre: {{ medicament?.name ? medicament.name : "" }}
                  </div>
                  <div class="text-overline mb-1">
                    Edad mínima de consumo:
                    {{ medicament?.minimumAgeConsumption }}
                    {{ medicament?.minimumAgeConsumption ? "años" : "" }}
                  </div>
                  <div class="text-overline mb-1">
                    Edad máxima de consumo: {{ medicament?.maxAgeConsumption }}
                    {{ medicament?.maxAgeConsumption ? "años" : "" }}
                  </div>
                  <div class="text-overline mb-1">
                    Uso exclusivo:
                    {{ Utils.getGender(medicament?.exclusiveUse) }}
                  </div>
                </div>
              </v-card-item>
              <v-card-actions>
                <v-autocomplete
                  v-model="medicament"
                  item-title="name"
                  label="Buscar Medicamento"
                  :items="medicaments"
                  variant="solo"
                  return-object
                ></v-autocomplete>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-form>
    <div class="text-center">
      <v-btn
        color="success"
        size="x-large"
        class="me-4"
        @click="savePrescription()"
        :disabled="!(patient != null && medicament != null)"
      >
        Guardar Prescripción
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

.box-container {
  margin: auto;
  width: 80%;
}
</style>
