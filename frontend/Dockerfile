# Use the official Node.js image as the build environment
FROM node:lts AS build-stage

# Set the working directory in the container
WORKDIR /src

# Copy the package.json and package-lock.json files to the working directory
COPY package*.json ./

# Install the app dependencies
RUN npm install

# Copy the entire app directory to the working directory
COPY . .

# Build the Vue app for production
RUN npm run build

# Use the official Nginx base image for serving the app
FROM nginx

# Remove the default Nginx configuration file
RUN rm /etc/nginx/conf.d/default.conf

# Copy the custom Nginx configuration file
COPY nginx.conf /etc/nginx/conf.d

# Copy the built Vue app files from the build-stage to the Nginx document root directory
COPY --from=build-stage /src/dist /usr/share/nginx/html

# Expose port 4200
EXPOSE 4200

# Start the Nginx server
CMD ["nginx", "-g", "daemon off;"]