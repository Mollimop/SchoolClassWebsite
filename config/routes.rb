Rails.application.routes.draw do
  root 'main#home'
  get 'home' => 'main#home'
  get 'login' => 'main#login'
  resources :threads
  resources :homeworks
  resources :events
  resources :answers
  resources :users
end
