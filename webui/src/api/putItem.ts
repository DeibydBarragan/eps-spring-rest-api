import { Endpoint } from "./types"

export const putItem = async (endpoint: Endpoint, item: object, id:number) => {
  const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/${endpoint}/${id}`, {
    method: 'PUT',
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
    throw new Error(data.message ? data.message : 'Hubo un error al actualizar el objeto')
  }
}