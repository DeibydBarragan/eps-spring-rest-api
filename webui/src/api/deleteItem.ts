export const deleteItem = async (endpoint:string) => {
  const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/${endpoint}`, {
    method: 'DELETE',
    headers: {
      'Content-type': 'application/json',
    },
  })
  const data = await res.json()

  if (!res.ok) {
    // Code for errors
    console.log(res)
    console.log(data)
    throw new Error(data.message ? data.message : 'Hubo un error al eliminar el objeto')
  }
}