Rails.application.routes.draw do
  root 'main#home'
  get 'logout' => 'users#logout'
  get 'home' => 'main#home'
  get 'login' => 'main#login'
  resources :threads
  resources :homeworks
  resources :events
  resources :answers
  resources :users
end
