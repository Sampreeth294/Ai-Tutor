/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        brand: {
          DEFAULT: '#7c3aed',
          accent: '#22d3ee',
        },
      },
      boxShadow: {
        glow: '0 0 60px -15px rgba(124, 58, 237, 0.8)',
      },
    },
  },
  plugins: [],
}





