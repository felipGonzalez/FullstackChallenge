<!-- eslint-disable prettier/prettier -->
<script setup lang="ts">
import { onBeforeMount, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import EventService from "@/services/EventService";
import { inject } from "vue";
import Alerts from "@/utils/Alerts";
import Utils from "@/utils/Utils";

const patients = ref({});
const router = useRoute();
const routerLink = useRouter();
const Swal = inject("$swal");
const page = ref(0);
const totalElements = ref(0);
const totalPages = ref(0);
const size = ref(0);
const listSize = [5, 10, 20, 50, 100];
const filterText = ref("");
const filterGender = ref("");
const typeGender = [
  { name: "Todos", value: "" },
  { name: "Femenino", value: "FEMALE" },
  { name: "Masculino", value: "MALE" },
];

onBeforeMount(async () => {
  loadData(1, 5);
});

const loadData = async (pageValue: number, sizeValue: number) => {
  const data = await EventService.searchPatients(
    pageValue - 1,
    sizeValue,
    filterText.value,
    filterGender.value
  );
  patients.value = data.data;
  updateConfigPage();
};

const updateConfigPage = () => {
  page.value = patients.value.number + 1;
  totalElements.value = patients.value.numberOfElements;
  totalPages.value = patients.value.totalPages;
  size.value = patients.value.size;
};

const checkActionDeleted = (id: number) => {
  Swal({
    title: "¿Estas seguro?",
    text: "¡No podrás revertir esto!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    cancelButtonText: "Cancelar",
    confirmButtonText: "¡Sí, bórralo!",
  }).then((result: any) => {
    if (result.isConfirmed) {
      deletePatient(id);
    }
  });
};

const deletePatient = (id: number) => {
  EventService.deletePatient(id).then(
    (res) => {
      Swal("Eliminado!", "El paciente se ha eliminado.", "success");
      loadData(1, 5);
    },
    (err) => {
      Alerts.showErrorMessage(err.patients.data.message);
    }
  );
};

const navigateTo = (path: {}) => {
  routerLink.push(path);
};
</script>

<template>
  <h2 class="text-center">Challenge-fullstack Felipe Gonzalez</h2>
  <div>
    <div class="d-flex justify-space-between mb-10">
      <v-btn
        prepend-icon="mdi-account-plus"
        elevation="2"
        large
        x-large
        @click="navigateTo({ name: 'patient' })"
        >Agregar Paciente</v-btn
      >
      <v-btn
        prepend-icon="mdi-account-plus"
        elevation="2"
        large
        x-large
        @click="navigateTo({ name: 'prescription' })"
        >Crear Prescripción</v-btn
      >
    </div>
    <v-row>
      <v-col cols="6">
        <v-text-field
          density="comfortable"
          variant="solo"
          label="Buscar por nombre"
          append-inner-icon="mdi-magnify"
          single-line
          hide-details
          v-model="filterText"
          @input="loadData(page, size)"
        ></v-text-field>
      </v-col>
      <v-col cols="6">
        <v-expansion-panels>
          <v-expansion-panel>
            <v-expansion-panel-title>
              <v-row no-gutters>
                <v-col cols="4" class="d-flex justify-start">
                  Filtros de busqueda
                </v-col>
              </v-row>
            </v-expansion-panel-title>
            <v-expansion-panel-text>
              <div class=" d-flex justify-space-between">
                <label class="box-gender" for="selectGender">Genero:</label>
                <select
                  id="selectGende r"
                  v-model="filterGender"
                  class="form-select"
                  aria-label="Genero"
                  @change="loadData(page, size)"
                >
                  <option v-for="option in typeGender" :value="option.value">
                    {{ option.name }}
                  </option>
                </select>
              </div>
            </v-expansion-panel-text>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-col>
    </v-row>
    <v-table>
      <thead>
        <tr>
          <th class="text-left">ID</th>
          <th class="text-left">Nombre</th>
          <th class="text-left">Fecha de nacimiento</th>
          <th class="text-left">Edad</th>
          <th class="text-left">Genero</th>
          <th class="text-left">Acciones</th>
          <th class="text-left">Prescripciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in patients.content" :key="item.name">
          <td>{{ item.id }}</td>
          <td>{{ item.name }} {{ item.lastName }}</td>
          <td>{{ item.birthDate }}</td>
          <td>{{ Utils.calculateAge(item?.birthDate) }}</td>
          <td>{{ Utils.getGender(item?.gender) }}</td>
          <td>
            <div class="d-flex justify-space-around">
              <v-btn
                class="ma-2"
                variant="text"
                icon="mdi-account-edit"
                color="green-darken-2"
                @click="
                  navigateTo({ name: 'patient', params: { id: item.id } })
                "
              ></v-btn>
              <v-btn
                class="ma-2"
                variant="text"
                icon="mdi-delete"
                color="blue-darken-2"
                @click="checkActionDeleted(item.id)"
              ></v-btn>
            </div>
          </td>
          <td>
            <div class="d-flex justify-space-around">
              <v-btn
                class="ma-2"
                variant="text"
                icon="mdi-tab-plus"
                color="purple-darken-2"
                :disabled="!item.isPrescriptionAllowed"
                @click="
                  navigateTo({ name: 'prescription', params: { id: item.id } })
                "
              ></v-btn>
              <v-btn
                class="ma-2"
                variant="text"
                icon="mdi-view-list"
                color="orange-darken-2"
                @click="
                  navigateTo({
                    name: 'prescription-user',
                    params: { id: item.id },
                  })
                "
              ></v-btn>
            </div>
          </td>
        </tr>
      </tbody>
    </v-table>
    <v-row justify="center">
      <v-col cols="8">
        <v-container class="d-flex justify-space-around">
          <v-pagination
            v-model="page"
            class="my-4"
            :length="totalPages"
            :total-visible="7"
            @click="loadData(page, size)"
          ></v-pagination>
          <select
            v-model="size"
            class="box-select form-select"
            aria-label="Tamaño"
            @change="loadData(page, size)"
          >
            <option v-for="option in listSize" :value="option">
              {{ option }}
            </option>
          </select>
        </v-container>
      </v-col>
    </v-row>
  </div>
</template>

<style lang="css" scoped>
.box-select {
  max-height: 40px;
  margin-top: 25px;
  max-width: 100px;
}
.box-gender {
  margin-top: 5px;
  padding-right: 5px;
}
</style>
