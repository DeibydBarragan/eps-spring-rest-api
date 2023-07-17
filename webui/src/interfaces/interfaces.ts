export interface Patient  {
  id: number
  name: string
  lastname: string
  cedula: number
  age: number
  email: string
  phone: number
}

export interface Doctor {
  id: number;
  name: string
  lastname: string
  cedula: number
  specialty: Specialty
  office: string
  email: string
  phone: number
}

export interface Appointment {
  patientId: number
  doctorId: number
  date: string
}

export type Specialty = 'Medicina general' | 'Cardiología' | 'Medicina interna' | 'Dermatología' | 'Rehabilitación física' | 'Psicología' | 'Odontología' | 'Radiología'

interface Sort {
  empty: boolean
  sorted: boolean
  unsorted: boolean
}
interface Pageable {
  sort: Sort
  offset: number
  pageNumber: number
  pageSize: number
  paged: boolean
  unpaged: boolean
}
export interface Pagination {
  pageable: Pageable
  last: boolean
  totalPages: number
  totalElements: number
  size: number
  number: number
  sort: Sort
  numberOfElements: number
  first: boolean
  empty: boolean
}