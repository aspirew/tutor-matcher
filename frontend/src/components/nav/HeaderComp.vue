<template>
  <header>
    <div class="header">
      <div class="container-header container-fluid">
        <div class="left">
          <slot name="left" />
        </div>
        <div class="center">
          <slot />
        </div>
        <div class="right">
          <slot name="right" />
        </div>
      </div>
    </div>
  </header>
</template>

<styles lang="scss">
header {
  .header {
    background: orange;
    position: fixed;
    top: 0;
    transform: translateZ(0);
    width: 100%;
    z-index: 99;
    padding: 0 10px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    padding: 20px;
    font-size: 20px;
  }

  .container-header {
    display: flex;
    flex: 1 0 auto;
    height: 20px;
    position: relative;
    margin-left: auto;
    margin-right: auto;

    .left {
      display: flex;
      gap: 15px;
      align-items: center;
      align-self: center;
      flex-shrink: 0;
      margin-right: 18px;
      height: 18px;
    }

    .center {
      display: flex;
      background-color: red;
      height: 10px;
      box-sizing: border-box;
    }

    .right {
      display: flex;
      gap: 15px;
      margin-left: auto;
    }
  }
}
</styles>
