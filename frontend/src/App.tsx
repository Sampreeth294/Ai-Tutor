import { useState } from 'react'

const features = [
  'Interactive lessons',
  'AI generated practice quizzes',
  'Progress dashboard',
  '24/7 mentor chat',
]

function App() {
  const [count, setCount] = useState(0)

  return (
    <div className="min-h-screen bg-slate-950 text-slate-50">
      <div className="mx-auto flex max-w-5xl flex-col px-6 py-16">
        <header className="mb-12">
          <p className="mb-3 inline-flex items-center gap-2 rounded-full bg-slate-900/70 px-4 py-1 text-sm text-slate-300 ring-1 ring-white/5">
            <span className="h-2 w-2 rounded-full bg-emerald-400" />
            Now powered by Tailwind CSS
          </p>
          <div className="flex flex-col gap-6 md:flex-row md:items-center md:justify-between">
            <div>
              <h1 className="text-4xl font-bold text-white sm:text-5xl">
                Build an AI Tutor experience
              </h1>
              <p className="mt-4 text-lg text-slate-300">
                Tailored study plans, instant feedback, and delightful UI tooling
                courtesy of Tailwind CSS.
              </p>
            </div>
            <div className="rounded-3xl border border-white/10 bg-slate-900/60 px-6 py-4 text-center shadow-glow">
              <p className="text-sm uppercase tracking-wide text-slate-400">
                Active learners
              </p>
              <p className="text-3xl font-semibold text-emerald-300">
                {25 + count}k
              </p>
              <button
                className="mt-3 w-full rounded-2xl bg-gradient-to-r from-brand to-brand-accent px-4 py-2 text-sm font-semibold text-slate-950 shadow-lg shadow-brand/40 transition hover:scale-[1.02]"
                onClick={() => setCount((prev) => prev + 1)}
              >
                Boost stats
              </button>
            </div>
          </div>
        </header>

        <main className="grid gap-6 md:grid-cols-[2fr,1fr]">
          <section className="rounded-3xl border border-white/10 bg-slate-900/60 p-8 shadow-2xl shadow-black/40">
            <h2 className="text-2xl font-semibold text-white">Why Tailwind?</h2>
            <p className="mt-3 text-slate-300">
              Utility-first styling keeps components consistent while letting you
              move fast. Start customizing this landing page to match your tutor
              brand.
            </p>
            <ul className="mt-6 space-y-3 text-slate-200">
              {features.map((feature) => (
                <li key={feature} className="flex items-center gap-3">
                  <span className="inline-flex h-6 w-6 items-center justify-center rounded-full bg-emerald-500/20 text-emerald-300">
                    ✓
                  </span>
                  {feature}
                </li>
              ))}
            </ul>
          </section>

          <aside className="rounded-3xl border border-white/10 bg-slate-900/60 p-6 text-sm text-slate-300 shadow-xl shadow-black/40">
            <p className="text-xs uppercase tracking-widest text-slate-400">
              Quick tips
            </p>
            <ul className="mt-4 space-y-4">
              <li>
                Edit <code className="text-emerald-300">tailwind.config.js</code>{' '}
                to add brand colors.
              </li>
              <li>Customize global styles in `src/index.css`.</li>
              <li>
                Start building components inside{' '}
                <code className="text-emerald-300">App.tsx</code>.
              </li>
              <li>
                Run <code className="text-emerald-300">npm run dev</code> to
                preview.
              </li>
            </ul>
          </aside>
        </main>
      </div>
    </div>
  )
}

export default App
