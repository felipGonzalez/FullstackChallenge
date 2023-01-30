import type { Patient } from '@/models/Patient';
import axios from 'axios';
import HttpClient from '@/services/httpClient'

export default {
  searchPatients(page:number,size:number,text:string,gender:string) {
    return HttpClient.get(`/patient/page?page=${page}&size=${size}&text=${text}&gender=${gender}`);
  },
  getPatient(id:number) {
    return HttpClient.get('/patient/'+id);
  },
  savePatient(patient:Patient) {
    return HttpClient.post('/patient/create',patient);
  },
  updatePatient(patient:Patient) {
    return HttpClient.put('/patient/update',patient);
  },
  deletePatient(id:number){
    return HttpClient.delete('patient/?idUser='+id);
  },
  searchUsers(text:string){
    return HttpClient.get('/patient?textToSearch='+text)
  },
  getAllMedicaments(){
    return HttpClient.get('medicament');
  },
  searchMedicaments(text:string){
    return HttpClient.get('/medicament?textToSearch='+text)
  },
  savePrescription(prescription:{}){
    return HttpClient.post('/prescription',prescription);
  },
  loadPrescriptionUser(patientId:number,page:number,size:number){
    return HttpClient.get(`/prescription/page?patientId=${patientId}&page=${page}&size=${size}`)
  }
  
}