import { Appointment, Doctor, Patient } from "@/interfaces/interfaces"
import { Endpoint } from "./types"

export const insertItem = async (endpoint: Endpoint, item: Patient | Doctor | Appointment) => {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/${endpoint}`, {
      method: 'POST',
      headers: {
        'Content-type': 'application/json',
      },
      body: JSON.stringify(item),
    })
    const data = await res.json()

    console.log(res)
    console.log(data)

    if (!res.ok) {
        // Code for errors
        console.log(res)
        console.log(data)
        throw new Error(data.message ? data.message : 'Hubo un error al insertar el objeto')
    }
}