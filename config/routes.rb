Rails.application.routes.draw do
  root 'main#login'
  get 'home' => 'main#home'
  resources :threads
  resources :homeworks
  resources :events
  resources :answers
end
