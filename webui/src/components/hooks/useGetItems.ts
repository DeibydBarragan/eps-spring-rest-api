import { fetchItems } from '@/api/fetchItems'
import { Pagination } from '@/interfaces/interfaces'
import React, { useState, useEffect, useCallback } from 'react'

type UseGetItemsResult = [
  items: any[],
  loadingItems: boolean,
  pagination: Pagination | null,
  actualPage: number,
  setActualPage: React.Dispatch<React.SetStateAction<number>>,
  getItems: () => void,
  error: string | null,
  setItems: React.Dispatch<React.SetStateAction<any[]>>
]

const useGetItems = (endpoint: string, limit: number = 7, page: number = 1): UseGetItemsResult => {
  const [items, setItems] = useState<any[]>([])
  const [loadingItems, setLoadingItems] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [pagination, setPagination] = useState<Pagination | null>(null)
  const [actualPage, setActualPage] = useState(page)

  
  const getItems = useCallback(() => {
    setError(null)
    setLoadingItems(true)
    fetchItems(endpoint, limit, actualPage)
    .then((data) => {
      setItems(data.content)
      setPagination(() => {
        const { docs, ...rest } = data
        return rest
      })
    })
    .catch((error) => {
      setError(error.message)
    })
    .finally(() => setLoadingItems(false))
  }, [endpoint, limit, actualPage])

  useEffect(() => {
    getItems()
  }, [getItems])

  return [items, loadingItems, pagination, actualPage, setActualPage, getItems, error, setItems]
}

export default useGetItems

