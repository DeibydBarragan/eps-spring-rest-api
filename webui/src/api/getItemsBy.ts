export const getItemsBy = async (endpoint:string, category:string, equalTo:string) => {
  const url = `${process.env.NEXT_PUBLIC_API_URL}/${endpoint}?${category}=${equalTo}`
  console.log(url)
  const res = await fetch(url)
  const data = await res.json()
  if (!res.ok) {
    // Code for errors
    console.log(res)
    console.log(data)
    throw new Error(data.message ? data.message : 'Hubo un error obtener los objetos')
  }
  return data
}