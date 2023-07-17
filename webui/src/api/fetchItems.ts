export const fetchItems = async (endpoint:string, limit:number = 7, page:number = 1) => {
  const url = `${process.env.NEXT_PUBLIC_API_URL}/${endpoint}size=${limit}&page=${page - 1}`
  const res = await fetch(url)
  const data = await res.json()
  if (!res.ok) {
    // Code for errors
    console.log(res)
    console.log(data)
    throw new Error(data.message ? data.message : 'Hubo un error al obtener los objetos')
  }
  return data
}