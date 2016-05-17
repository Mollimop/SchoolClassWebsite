Rails.application.routes.draw do
  root 'main#home'
  get 'home' => 'main#home'
  resources :threads
  resources :homeworks
  resources :events
  resources :answers
end
