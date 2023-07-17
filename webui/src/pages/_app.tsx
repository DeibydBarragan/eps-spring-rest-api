import '@/styles/globals.css'
import 'react-toastify/dist/ReactToastify.css';
import { createTheme, NextUIProvider } from '@nextui-org/react'
import type { AppProps } from 'next/app'
import { ToastContainer } from 'react-toastify'

export default function App({ Component, pageProps }: AppProps) {

  const DarkTheme = createTheme({
    type: 'dark',
    theme: {
      colors: {
        background: '#1d1d1d',
        text: '#fff',
        DarkColor: '#ff4ecd',
      },
      space: {},
      fonts: {}
    }
  })

  return (
    <>
      <NextUIProvider theme={DarkTheme}>
        <Component {...pageProps} />
      </NextUIProvider>
      <ToastContainer
        position="bottom-right"
        autoClose={7000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="dark"
        style={{
          zIndex: 99999,
        }}
      />
    </>
  )
}
