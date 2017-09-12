const mix = require('laravel-mix')

mix.sass('resources/sass/app.scss', 'resources/public/css/app.css')
  .sourceMaps()
